import mpi.*;

public class CollectiveMultiplication {
    public static void main(String[] args) {
        int rowsA = 1500;
        int colsA = 1500;
        int colsB = 1500;
        long startTime = 0;

        Generator.generateMatrix(rowsA, colsA, MatrixType.TRIANGLE, 1, "matrixA.txt");
        Generator.generateMatrix(colsA, colsB, MatrixType.TRIANGLE, 1, "matrixB.txt");

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
            startTime = System.currentTimeMillis();
        }

        MPI.COMM_WORLD.Bcast(matrixB, 0, colsA, MPI.OBJECT, 0); // Broadcast matrixB to all processes

        int rowsPerProcess = rowsA / size;
        int extraRows = rowsA % size;
        int[] sendcounts = new int[size];
        int[] displs = new int[size];

        for (int i = 0; i < size; i++) {
            sendcounts[i] = (i < extraRows) ? rowsPerProcess + 1 : rowsPerProcess;
            displs[i] = (i > 0) ? displs[i - 1] + sendcounts[i - 1] : 0;
        }

        int[][] rowBuffer = new int[rowsPerProcess][rowsA];
        MPI.COMM_WORLD.Scatterv(matrixA, 0, sendcounts, displs, MPI.OBJECT, rowBuffer, 0, sendcounts[rank], MPI.OBJECT, 0); // Scatter matrixA

        MPI.COMM_WORLD.Bcast(matrixB, 0, colsA, MPI.OBJECT, 0); // Broadcast matrixB to all processes
        int[][] localMatrixC = new int[sendcounts[rank]][colsB];

        for (int k = 0; k < colsA; k++) {
            for (int i = 0; i < sendcounts[rank]; i++) {
                localMatrixC[i][k] = 0;
                for (int j = 0; j < colsA; j++) {
                    localMatrixC[i][k] += rowBuffer[i][j] * matrixB[j][k];
                }
            }
        }

        MPI.COMM_WORLD.Gatherv(localMatrixC, 0, sendcounts[rank], MPI.OBJECT, matrixC, 0, sendcounts, displs, MPI.OBJECT, 0); // Gather localMatrixC

        if (rank == 0) {
            long endTime = System.currentTimeMillis();

            /*for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < colsB; j++) {
                    System.out.print(matrixC[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("Time: " + (endTime - startTime) + " ms");*/
            System.out.println(endTime - startTime);
        }

        MPI.Finalize();
    }
}

