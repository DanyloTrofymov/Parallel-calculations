import mathThreads.Multiplier;
import utils.Generator;
import utils.MatrixType;
import utils.Reader;
import utils.Result;

public class Main {

    //        int[][] matrixA = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    //        int[][] matrixB = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    public static void main(String[] args) {
        int[][] matrixA = Reader.readMatrix("scalar.txt");
        int[][] matrixB = Reader.readMatrix("scalar.txt");
        Result stripedResult = Multiplier.stripedMultiply(matrixA, matrixB, 1);
        System.out.println("Striped:");
        stripedResult.printTime();

        Result foxResult = Multiplier.stripedMultiply(matrixA, matrixB, 1);
        System.out.println("Fox:");
        foxResult.printTime();

        //Generator.generateMatrix(1000, 1000, MatrixType.RANDOM, "matrixA.txt");
        //Generator.generateMatrix(1000, 1000, MatrixType.RANDOM, "matrixB.txt");
    }
}
