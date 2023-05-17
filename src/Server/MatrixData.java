package Server;

public class MatrixData {
    private String mode;
    private int[][] matrixA;
    private int[][] matrixB;

    public MatrixData(String mode, int[][] matrixA, int[][] matrixB) {
        this.mode = mode;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public String getMode() {
        return mode;
    }

    public int[][] getMatrixA() {
        return matrixA;
    }

    public int[][] getMatrixB() {
        return matrixB;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setMatrixA(int[][] matrixA) {
        this.matrixA = matrixA;
    }

    public void setMatrixB(int[][] matrixB) {
        this.matrixB = matrixB;
    }
}