package math;

public abstract class Multiplier extends Thread {
    protected int[][] matrixA;
    protected int[][] matrixB;
    protected int[][] result;
    protected int row;
    public Multiplier(int[][] result, int[][] matrixA, int[][] matrixB, int row) {
        if (matrixA[0].length != matrixB.length)
            throw new IllegalArgumentException("Matrix A's column count must match matrix B's row count.");
        this.result = result;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.row = row;
    }
    public abstract void run();

    public int[][] getResult() {
        return result;
    }

    public void setMatrixA(int[][] matrixA) {
        this.matrixA = matrixA;
    }
    public void setMatrixB(int[][] matrixB) {
        this.matrixB = matrixB;
    }
}
