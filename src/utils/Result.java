package utils;

import java.io.File;
import java.io.FileWriter;

public class Result {
    private final int[][] matrix;
    private final long time;

    public Result(int[][] matrix, long time) {
        this.matrix = matrix;
        this.time = time;
    }

    public void writeResult() {
        int columns = matrix[0].length;
        try {
            File file = new File("result.txt");
            FileWriter fileWriter = new FileWriter(file);
            for (int[] ints : matrix) {
                for (int j = 0; j < columns; j++) {
                    fileWriter.write(ints[j] + " ");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    public void printTime() {
        System.out.println("Time: " + time + " ms");
    }

}
