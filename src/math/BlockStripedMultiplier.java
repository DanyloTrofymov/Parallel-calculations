package math;

public class BlockStripedMultiplier extends Multiplier{

    public BlockStripedMultiplier(int[][] result, int[][] matrixA, int[][] matrixB, int row) {
        super(result, matrixA, matrixB, row);
    }

    @Override
    public void run() {
        for (int i = 0; i < matrixB[0].length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result[row][j] += matrixA[row][i] * matrixB[j][i];
            }
        }
    }
}
