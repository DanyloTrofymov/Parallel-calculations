package utils;

import java.util.Scanner;

public class View {
    private final Scanner scanner;
    public View() {
        scanner = new Scanner(System.in);
    }
    public int getMatrixSize() {
        System.out.println("Enter matrix size: ");
        return scanner.nextInt();
    }
    public int getScalar() {
        System.out.println("Enter scalar: ");
        return scanner.nextInt();
    }
    public String getMatrixType() {
        System.out.println("Enter matrix type: ");
        return scanner.next();
    }
    public String getFileName() {
        System.out.println("Enter file name: ");
        return scanner.next();
    }
    public void printResult(Result result) {
        result.printTime();
        result.writeResult();
    }
}
