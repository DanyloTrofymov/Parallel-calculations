package math;

public class FoxMultiplier extends Multiplier{
    protected int blockSize;
    public FoxMultiplier(int[][]result, int[][] matrixA, int[][] matrixB, int row, int blockSize) {
        super(result, matrixA, matrixB, row);
        this.blockSize = blockSize;
    }

    @Override
    public void run() {

    }
}
