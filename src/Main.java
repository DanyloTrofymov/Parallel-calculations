import mathThreads.Multiplier;

import utils.Reader;
import utils.Result;

public class Main {

    //        int[][] matrixA = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    //        int[][] matrixB = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    public static void main(String[] args) {

        int[][] matrixA = Reader.readMatrix("1000.txt");
        int[][] matrixB = Reader.readMatrix("100.txt");

            Result stripedResult = Multiplier.stripedMultiply(matrixA, matrixB, 10);
            stripedResult.printTime();

            //Result foxResult = Multiplier.foxMultiply(matrixA, matrixB, matrixA.length / 10, 16);
            //foxResult.printTime();

        //Generator.generateMatrix(10000, 10000, MatrixType.RANDOM, "10000.txt");
        //Generator.generateMatrix(1000, 1000, MatrixType.RANDOM, "matrixB.txt");
    }
}
