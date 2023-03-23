package mathThreads;

import mathThreads.threads.FoxMultiplierThread;
import mathThreads.threads.StripedMultiplierThread;
import utils.Result;

import java.util.ArrayList;
import java.util.List;

public class Multiplier {
    public static Result stripedMultiply(int[][] matrixA, int[][] matrixB) {
        int matrixARows = matrixA.length;
        int matrixACols = matrixA[0].length;
        int matrixBRows = matrixA.length;
        int matrixBCols = matrixB[0].length;

        if (matrixACols != matrixBRows)
            throw new IllegalArgumentException("Matrix A's column count must match matrix B's row count.");

        int[][] result = new int[matrixARows][matrixBCols];

        long startTime = System.currentTimeMillis();
        List<StripedMultiplierThread> threads = new ArrayList<>();
        for (int i = 0; i < matrixARows; i++) {
            StripedMultiplierThread thread = new StripedMultiplierThread(result, matrixA, matrixB, i);
            threads.add(thread);
            thread.start();
        }
        for (StripedMultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime - startTime);
    }

    public static Result foxMultiply(int[][] matrixA, int[][] matrixB, int blockSize){
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

        List<FoxMultiplierThread> threads = new ArrayList<>();
        int blocksCount = matrixARows / blockSize;
        for (int i = 0; i < blocksCount; i++) {
            final int row = i;
            FoxMultiplierThread thread = new FoxMultiplierThread(blocksResult, blocksA, blocksB, row, blockSize);

            threads.add(thread);
            thread.start();
        }
        for (FoxMultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
