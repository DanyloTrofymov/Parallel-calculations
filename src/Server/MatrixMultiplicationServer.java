package Server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Reader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

public class MatrixMultiplicationServer extends WebSocketServer {
    public MatrixMultiplicationServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Client connected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Read matrix data from the client
        JSONObject jsonObject = new JSONObject(message);

        String mode = jsonObject.getString("mode");
        int[][] result = null;
        switch (mode) {
            case "client" -> result = handleClient(jsonObject);
            case "server" -> result = handleServer();
            default -> System.out.println("Invalid mode");
        }

        conn.send(convertMatrixToJSON(result));
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started successfully!");
    }
    public static void main(String[] args) {
        int portNumber = 3000;
        MatrixMultiplicationServer server = new MatrixMultiplicationServer(new InetSocketAddress(portNumber));
        server.start();
        System.out.println("Server started on port " + portNumber);
    }

    private int[][]  handleClient(JSONObject jsonObject) {
        // Read matrix data from the jsonObject
        JSONArray matrixAArray = jsonObject.getJSONArray("matrixA");
        JSONArray matrixBArray = jsonObject.getJSONArray("matrixB");

        // Convert the JSON arrays to 2D arrays
        int[][] matrixA = convertJSONArrayToMatrix(matrixAArray);
        int[][] matrixB = convertJSONArrayToMatrix(matrixBArray);

        // Perform matrix multiplication using the Server.Multiplier class
        int threadsCount = Runtime.getRuntime().availableProcessors();

        return Multiplier.stripedMultiply(matrixA, matrixB, threadsCount);
    }

    private int[][]  handleServer() {
        // Convert byte arrays to matrices
        int[][] matrixA = Reader.readMatrix("matrixA.txt");
        int[][] matrixB = Reader.readMatrix("matrixB.txt");

        // Perform matrix multiplication using the Server.Multiplier class
        int threadsCount = Runtime.getRuntime().availableProcessors();
        int[][] result = Multiplier.stripedMultiply(matrixA, matrixB, threadsCount);

        // Send the result back to the client
        return Multiplier.stripedMultiply(matrixA, matrixB, threadsCount);
    }

    private int[][] convertJSONArrayToMatrix(JSONArray jsonArray) {
        int rows = jsonArray.length();
        int cols = jsonArray.getJSONArray(0).length();
        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            JSONArray rowArray = jsonArray.getJSONArray(i);
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rowArray.getInt(j);
            }
        }

        return matrix;
    }
    private String convertMatrixToJSON(int[][] matrix) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (int[] row : matrix) {
            JSONArray rowArray = new JSONArray();
            for (int value : row) {
                rowArray.put(value);
            }
            jsonArray.put(rowArray);
        }

        return jsonArray.toString();
    }

}