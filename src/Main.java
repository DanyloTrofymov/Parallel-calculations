import mathThreads.Multiplier;
import utils.Result;

public class Main {
    public static void main(String[] args) {
        int[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrixB = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Result result = Multiplier.stripedMultiply(matrixA, matrixB);
        result.printTime();
        result.writeResult();
    }
}
