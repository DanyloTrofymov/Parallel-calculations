package Client;

import utils.Generator;
import utils.Reader;

import java.io.*;
import java.net.Socket;

import utils.MatrixType;

class MatrixMultiplicationClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 3000;

    public static void main(String[] args) {
        switch (args[0]) {
            case "client" -> {
                if (args.length != 4) {
                    System.out.println("Usage: java Client.MatrixMultiplicationClient client <matrixAPath> <matrixBPath> <resultPath>");
                    System.exit(1);
                } else {
                    String matrixAPath = args[1];
                    String matrixBPath = args[2];
                    String resultPath = args[3];
                    sendMatricesFromClient(matrixAPath, matrixBPath, resultPath);
                }
            }
            case "server" -> {
                if (args.length != 2) {
                    System.out.println("Usage: java Client.MatrixMultiplicationClient server <resultPath>");
                    System.exit(1);
                }
                else {
                    String resultPath = args[1];
                    sendMatricesSize(resultPath);
                }
            }
            default -> {
                System.out.println("Usage: java Client.MatrixMultiplicationClient <modeOfData> ... \nModes: client, server");
                System.exit(1);
            }
        }
    }

    public static byte[] convertMatrixToByteArray(int[][] matrix) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(matrix);
            objectStream.flush();
            return byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendMatricesFromClient(String matrixAPath, String matrixBPath, String resultPath) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);) {
            long startTime = System.currentTimeMillis();
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            int[][] matrixA = Reader.readMatrix(matrixAPath);
            int[][] matrixB = Reader.readMatrix(matrixBPath);

            // Read file contents into byte arrays
            byte[] modeBytes = "client".getBytes();
            byte[] matrixABytes = convertMatrixToByteArray(matrixA);
            byte[] matrixBBytes = convertMatrixToByteArray(matrixB);

            // Send matrix data to the server
            output.writeObject(modeBytes);
            output.writeObject(matrixABytes);
            output.writeObject(matrixBBytes);
            output.flush();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            int[][] result = (int[][]) input.readObject();
            long endTime = System.currentTimeMillis();

            Result resultObj = new Result(result, endTime - startTime);
            resultObj.printResult();
            resultObj.writeResult(resultPath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void sendMatricesSize(String resultPath) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            long startTime = System.currentTimeMillis();
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            // Read file contents into byte arrays
            byte[] modeBytes = "server".getBytes();

            // Send matrix data to the server
            output.writeObject(modeBytes);
            output.flush();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            int[][] result = (int[][]) input.readObject();
            long endTime = System.currentTimeMillis();
            Result resultObj = new Result(result, endTime - startTime);
            resultObj.printResult();
            resultObj.writeResult(resultPath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
