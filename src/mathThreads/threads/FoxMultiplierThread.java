package mathThreads.threads;

public class FoxMultiplierThread extends Thread {
    private int[][][][] blocksA;
    private int[][][][] blocksB;
    private int[][][][] result;
    private int row;
    private int blockSize;
    public FoxMultiplierThread(int[][][][] result, int[][][][] blocksA, int[][][][] blocksB, int row, int blockSize) {
        this.result = result;
        this.blocksA = blocksA;
        this.blocksB = blocksB;
        this.row = row;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        int matrixACols = blocksA[0].length;
        int matrixBCols = blocksB[0].length;

        for (int i = 0; i < matrixBCols; i++) {
            int[][] resultBlock = new int[blockSize][blockSize];
            for (int j = 0; j < matrixACols; j++) {
                int[][] subA = blocksA[row][j];
                int[][] subB = blocksB[j][i];

                int[][] subResult = multiplyBlocks(subA, subB);
                resultBlock = addBlocks(resultBlock, subResult);
            }
            result[row][i] = resultBlock;
        }
    }

    private int[][] multiplyBlocks(int[][] subA, int[][] subB) {
        int rowsA = subA.length;
        int colsA = subA[0].length;
        int colsB = subB[0].length;
        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += subA[i][k] * subB[k][j];
                }
            }
        }
        return result;
    }

    private int[][] addBlocks(int[][] blockA, int[][] blockB) {
        int blocksCount = blockA.length;
        int[][] result = new int[blocksCount][blocksCount];

        for (int i = 0; i < blocksCount; i++) {
            for (int j = 0; j < blocksCount; j++) {
                result[i][j] = blockA[i][j] + blockB[i][j];
            }
        }
        return result;
    }
}
