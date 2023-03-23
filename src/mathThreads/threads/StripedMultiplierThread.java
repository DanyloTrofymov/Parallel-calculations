package mathThreads.threads;

public class StripedMultiplierThread extends Thread{
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int row;
    public StripedMultiplierThread(int[][] result, int[][] matrixA, int[][] matrixB, int row) {
        this.result = result;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.row = row;
    }

    @Override
    public void run() {
        int matrixACols = matrixA[0].length;
        int matrixBCols = matrixB[0].length;
        for (int i = 0; i < matrixBCols; i++) {
            for (int j = 0; j < matrixACols; j++) {
                result[row][i] += matrixA[row][j] * matrixB[j][i];
            }
        }
    }
}
