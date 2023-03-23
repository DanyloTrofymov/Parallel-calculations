package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Reader {
    public static int[][] readMatrix(String path){

        int[][] matrix = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                int rowLength = values.length;
                int[] row = new int[rowLength];

                for (int i = 0; i < rowLength; i++) {
                    row[i] = Integer.parseInt(values[i]);
                }
                matrix = addRow(matrix, row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrix;
    }
    private  static int[][] addRow(int[][] matrix, int[] row) {
        int rowLength = row.length;
        int matrixLength = matrix == null ? 0 : matrix.length;

        if (matrix == null) {
            matrix = new int[1][rowLength];
            matrix[0] = row;
            return matrix;
        }
        int[][] newMatrix = new int[matrixLength + 1][rowLength];
        System.arraycopy(matrix, 0, newMatrix, 0, matrixLength);
        newMatrix[matrixLength] = row;
        return newMatrix;
    }
}
