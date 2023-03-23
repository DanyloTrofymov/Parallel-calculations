package mathThreads;

import mathThreads.threads.StripedMultiplierThread;
import utils.Result;

import java.util.ArrayList;
import java.util.List;

public class Multiplier {
    public static Result stripedMultiply(int[][] matrixA, int[][] matrixB) {
        int matrixARows = matrixA.length;
        int matrixACols = matrixA[0].length;
        int matrixBRows = matrixB.length;
        int matrixBCols = matrixB[0].length;
        int[][] result = new int[matrixARows][matrixBCols];

        long startTime = System.currentTimeMillis();
        List<StripedMultiplierThread> threads = new ArrayList<>();
        for (int i = 0; i < matrixARows; i++) {
            StripedMultiplierThread thread = new StripedMultiplierThread(result, matrixA, matrixB, i);
            threads.add(thread);
            thread.start();
        }
        for (StripedMultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime - startTime);
    }
}
