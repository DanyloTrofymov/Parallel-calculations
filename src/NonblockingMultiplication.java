import mpi.*;

import java.util.ArrayList;
import java.util.List;

public class NonblockingMultiplication {


    public static void main(String[] args) {
        for (int reps = 0; reps < 20; reps++) {
            int rowsA = 100;
            int colsA = 100;
            int colsB = 100;

            Generator.generateMatrix(rowsA, colsA, MatrixType.TRIANGLE, 1, "matrixA.txt");
            Generator.generateMatrix(colsA, colsB, MatrixType.TRIANGLE, 1, "matrixB.txt");
            int[] rows = {0}, offset = {0};
            int[][] matrixA = new int[rowsA][colsA];
            int[][] matrixB = new int[colsA][colsB];
            int[][] matrixC = new int[rowsA][colsB];

            MPI.Init(args);
            int rank = MPI.COMM_WORLD.Rank();
            int size = MPI.COMM_WORLD.Size();

            if (size < 2) {
                System.err.println("Need at least two MPI tasks. Quitting...");
                MPI.COMM_WORLD.Abort(1);
                return;
            }

            if (rank == 0) {
                matrixA = Reader.readMatrix("matrixA.txt");
                matrixB = Reader.readMatrix("matrixB.txt");
                long startTime = System.currentTimeMillis();
                int rowsPerProcess = rowsA / (size - 1);
                int extraRows = rowsA % (size - 1);

                for (int i = 1; i < size; i++) {
                    rows[0] = (i <= extraRows) ? rowsPerProcess + 1 : rowsPerProcess;
                    Request request1 = MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, i, 0);
                    Request request2 = MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, i, 1);
                    MPI.COMM_WORLD.Isend(matrixA, offset[0], rows[0], MPI.OBJECT, i, 2);
                    MPI.COMM_WORLD.Isend(matrixB, 0, colsA, MPI.OBJECT, i, 3);
                    offset[0] += rows[0];
                    request1.Wait();
                    request2.Wait();
                }
                ArrayList<Request> requests = new ArrayList<>();
                for (int i = 1; i < size; i++) {
                    Request request1 = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, i, 4);
                    Request request2 = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, i, 5);
                    request1.Wait();
                    request2.Wait();
                    requests.add(MPI.COMM_WORLD.Irecv(matrixC, offset[0], rows[0], MPI.OBJECT, i, 6));

                }
                for (Request request : requests) {
                    request.Wait();
                }
                long stopTime = System.currentTimeMillis();
                System.out.println("Time: " + (stopTime - startTime) + "ms");
                //   System.out.println(stopTime - startTime);

                for (int i = 0; i < rowsA; i++) {
                    for (int j = 0; j < colsB; j++) {
                        System.out.print(matrixC[i][j] + " ");
                    }
                    System.out.println();
                }

            } else {

                Request request1 = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, 0, 0);
                Request request2 = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, 0, 1);
                request1.Wait();
                request2.Wait();
                MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, 0, 4);
                MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, 0, 5);
                Request request3 = MPI.COMM_WORLD.Irecv(matrixA, 0, rows[0], MPI.OBJECT, 0, 2);
                Request request4 = MPI.COMM_WORLD.Irecv(matrixB, 0, colsA, MPI.OBJECT, 0, 3);
                request3.Wait();
                request4.Wait();

                for (int k = 0; k < colsA; k++) {
                    for (int i = 0; i < rows[0]; i++) {
                        matrixC[i][k] = 0;
                        for (int j = 0; j < colsA; j++) {
                            matrixC[i][k] += matrixA[i][j] * matrixB[j][k];
                        }
                    }
                }

                MPI.COMM_WORLD.Isend(matrixC, 0, rows[0], MPI.OBJECT, 0, 6);
            }
            MPI.Finalize();
        }
    }
}