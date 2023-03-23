package mathThreads.threads;

public class StripedMultiplierThread extends MultiplierThread {

    public StripedMultiplierThread(int[][] result, int[][] matrixA, int[][] matrixB, int row) {
        super(result, matrixA, matrixB, row);
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
