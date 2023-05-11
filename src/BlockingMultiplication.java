import mpi.MPI;

public class BlockingMultiplication {


    public static void main(String[] args) {

        int rowsA = 100;
        int colsA = 100;
        int colsB = 100;

        Generator.generateMatrix(rowsA, colsA, MatrixType.TRIANGLE, 1,   "matrixA.txt");
        Generator.generateMatrix(colsA, colsB, MatrixType.TRIANGLE, 1,  "matrixB.txt");
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
            int rowsPerProcess = rowsA / (size-1);
            int extraRows = rowsA % (size-1);

            for (int i = 1; i < size; i++) {
                rows[0] = (i <= extraRows) ? rowsPerProcess + 1 : rowsPerProcess;
                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, i, 0);
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, i, 0);
                MPI.COMM_WORLD.Send(matrixA, offset[0], rows[0], MPI.OBJECT, i, 0);
                MPI.COMM_WORLD.Send(matrixB, 0, colsA, MPI.OBJECT, i, 0);
                offset[0] += rows[0];
            }
            for (int i = 1; i < size; i++) {
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, i, 1);
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, i, 1);
                MPI.COMM_WORLD.Recv(matrixC, offset[0], rows[0], MPI.OBJECT, i, 1);

            }
            long stopTime = System.currentTimeMillis();
            System.out.println("Time: " + (stopTime - startTime) + "ms");
            for(int i = 0; i < rowsA; i++) {
                for(int j = 0; j < colsB; j++) {
                    System.out.print(matrixC[i][j] + " ");
                }
                System.out.println();
            }

        } else {
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, 0, 0);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, 0, 0);
            MPI.COMM_WORLD.Recv(matrixA, 0, rows[0], MPI.OBJECT, 0, 0);
            MPI.COMM_WORLD.Recv(matrixB, 0, colsA, MPI.OBJECT, 0, 0);

            for (int k = 0; k < colsA; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    matrixC[i][k] = 0;
                    for (int j = 0; j < colsA; j++) {
                        matrixC[i][k] += matrixA[i][j] * matrixB[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, 0, 1);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, 0, 1);
            MPI.COMM_WORLD.Send(matrixC, 0, rows[0], MPI.OBJECT, 0, 1);
        }
        MPI.Finalize();
    }
}