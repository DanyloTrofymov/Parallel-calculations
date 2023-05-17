package Server;

public class StripedMultiplierThread extends Thread{
    private int[] rowA;
    private int[] rowB;
    private int i;
    private int j;
    private int[][] result;
    private int row;
    public StripedMultiplierThread(int[][] result, int[] rowA, int[] rowB, int i, int j) {
        this.result = result;
        this.rowA = rowA;
        this.rowB = rowB;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run() {
        int matrixACols = rowA.length;
        for (int k = 0; k < matrixACols; k++) {
            result[i][j] += rowA[k] * rowB[k];
        }
    }
}