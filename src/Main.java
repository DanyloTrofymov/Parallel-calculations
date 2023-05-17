import mathThreads.Multiplier;

import utils.Generator;
import utils.MatrixType;
import utils.Reader;
import utils.Result;

public class Main {

    public static void main(String[] args) {
        int[][] matrixA = Reader.readMatrix("matrixA.txt");
        int[][] matrixB = Reader.readMatrix("matrixB.txt");
            for (int i = 0; i < 20; i++) {
                Result stripedResult = Multiplier.stripedMultiply(matrixA, matrixB, 10);
                stripedResult.printTime();
            }
            //Result foxResult = Multiplier.foxMultiply(matrixA, matrixB, matrixA.length / 10, 16);
            //foxResult.printTime();
    }
}
