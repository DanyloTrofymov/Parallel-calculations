package mathThreads;

import mathThreads.threads.FoxMultiplierThread;
import mathThreads.threads.StripedMultiplierThread;
import utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multiplier {
    public static Result stripedMultiply(int[][] matrixA, int[][] matrixB, int threadsCount){
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
        return new Result(result, finishTime - startTime);
    }

    private static int[][] transpose(int[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public static Result foxMultiply(int[][] matrixA, int[][] matrixB, int blockSize, int threadsCount){
        int matrixARows = matrixA.length;
        int matrixACols = matrixA[0].length;
        int matrixBRows = matrixB.length;
        int matrixBCols = matrixB[0].length;

        if (matrixACols != matrixBRows)
            throw new IllegalArgumentException("Matrix A's column count must match matrix B's row count.");

        if( matrixARows % blockSize != 0 || matrixACols % blockSize != 0 || matrixBRows % blockSize != 0 || matrixBCols % blockSize != 0)
            throw new IllegalArgumentException("Matrix dimensions must be a multiple of the block size.");

        long startTime = System.currentTimeMillis();

        int[][] result = new int[matrixARows][matrixBCols];
        for(int i = 0; i < matrixARows; i++){
            for(int j = 0; j < matrixBCols; j++){
                result[i][j] = 0;
            }
        }

        int[][][][] blocksA = getBlocks(matrixA, blockSize);
        int[][][][] blocksB = getBlocks(matrixB, blockSize);
        int[][][][] blocksResult = getBlocks(result, blockSize);

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Callable<Object>> todo = new ArrayList<>(matrixARows);
        int blocksCount = matrixARows / blockSize;
        for (int i = 0; i < blocksCount; i++) {
            FoxMultiplierThread thread = new FoxMultiplierThread(blocksResult, blocksA, blocksB, i, blockSize);
            todo.add(Executors.callable(thread));
        }

        try {
            executor.invokeAll(todo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

        convertToArray(blocksResult, result);

        long finishTime = System.currentTimeMillis();

        return new Result(result, finishTime - startTime);
    }

    public static int [][][][] getBlocks(int[][] matrix, int blockSize){
        int blocksCount = matrix.length / blockSize;
        int[][][][] blocks = new int[blocksCount][blocksCount][blockSize][blockSize];
        for(int i = 0; i < blocksCount; i++){
            for(int j = 0; j < blocksCount; j++){
                for(int k = 0; k < blockSize; k++){
                    System.arraycopy(matrix[i * blockSize + k], j * blockSize, blocks[i][j][k], 0, blockSize);
                }
            }
        }
        return blocks;
    }

    public static void convertToArray(int[][][][] blocks, int[][] matrix){
        int subSize = blocks[0][0].length;
        int subCount = blocks.length;

        for (int i = 0; i < subCount; i++) {
            for (int j = 0; j < subCount; j++) {
                int[][] subMatrix = blocks[i][j];
                int subMatrixStartRow = i * subSize;
                int subMatrixStartCol = j * subSize;
                for (int k = 0; k < subSize; k++) {
                    System.arraycopy(subMatrix[k], 0, matrix[subMatrixStartRow + k], subMatrixStartCol, subSize);
                }
            }
        }
    }
}
