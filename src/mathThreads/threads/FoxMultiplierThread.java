package mathThreads.threads;

public class FoxMultiplierThread extends MultiplierThread {
    protected int blockSize;
    public FoxMultiplierThread(int[][]result, int[][] matrixA, int[][] matrixB, int row, int blockSize) {
        super(result, matrixA, matrixB, row);
        this.blockSize = blockSize;
    }

    @Override
    public void run() {

    }
}
