package Server;

import utils.Reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Read matrix data from the client
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
            byte[] modeBytes = (byte[]) input.readObject();
            String mode = new String(modeBytes);

            switch (mode) {
                case "client" -> handleClient(input);
                case "server" -> handleServer();
                default -> System.out.println("Invalid mode");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(ObjectInputStream input) {
        try {
            // Read matrix data as byte arrays
            byte[] matrixABytes = (byte[]) input.readObject();
            byte[] matrixBBytes = (byte[]) input.readObject();

            // Convert byte arrays to matrices
            int[][] matrixA = convertByteArrayToMatrix(matrixABytes);
            int[][] matrixB = convertByteArrayToMatrix(matrixBBytes);

            // Perform matrix multiplication using the Server.Multiplier class
            int threadsCount = Runtime.getRuntime().availableProcessors();
            int[][] result = Multiplier.stripedMultiply(matrixA, matrixB, threadsCount);

            // Send the result back to the client
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            output.writeObject(result);
            output.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleServer() {
        try {
            // Convert byte arrays to matrices
            int[][] matrixA = Reader.readMatrix("matrixA.txt");
            int[][] matrixB = Reader.readMatrix("matrixB.txt");

            // Perform matrix multiplication using the Server.Multiplier class
            int threadsCount = Runtime.getRuntime().availableProcessors();
            int[][] result = Multiplier.stripedMultiply(matrixA, matrixB, threadsCount);

            // Send the result back to the client
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            output.writeObject(result);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] convertByteArrayToMatrix(byte[] byteArray) {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArray);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return (int[][]) objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;  // Handle the exception appropriately in your code
        }
    }
}