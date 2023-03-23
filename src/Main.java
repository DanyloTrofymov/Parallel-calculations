import mathThreads.Multiplier;
import utils.Generator;
import utils.MatrixType;
import utils.Reader;
import utils.Result;

public class Main {

    //        int[][] matrixA = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    //        int[][] matrixB = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    public static void main(String[] args) {
        int[][] matrixA = Reader.readMatrix("matrixA.txt");
        int[][] matrixB = Reader.readMatrix("matrixB.txt");
        Result result = Multiplier.foxMultiply(matrixA, matrixB, 100);
        result.printTime();
        result.writeResult();
    }
}
