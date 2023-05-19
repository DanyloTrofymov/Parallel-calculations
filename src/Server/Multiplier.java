package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multiplier {
    public static int[][] stripedMultiply(int[][] matrixA, int[][] matrixB, int threadsCount) throws IllegalArgumentException {
        int matrixARows = matrixA.length;
        int matrixACols = matrixA[0].length;
        int matrixBRows = matrixB.length;
        int matrixBCols = matrixB[0].length;

        if (matrixACols != matrixBRows)
            throw new IllegalArgumentException("Matrix A's column count must match matrix B's row count.");

        int[][] result = new int[matrixARows][matrixBCols];
        int[][] transposedMatrixB = transpose(matrixB);

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Callable<Object>> todo = new ArrayList<>(matrixARows);
        for (int i = 0; i < matrixARows; i++) {
            for (int j = 0; j < matrixBCols; j++) {
                StripedMultiplierThread thread = new StripedMultiplierThread(result, matrixA[i], transposedMatrixB[j], i, j);
                todo.add(Executors.callable(thread));
            }
        }
        try {
            executor.invokeAll(todo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
        long finishTime = System.currentTimeMillis();
        return result;
    }

    private static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }
}