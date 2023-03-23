import utils.Generator;
import utils.MatrixType;

public class Main {
    public static void main(String[] args) {
        Generator.generateMatrix(100,100, MatrixType.SCALAR, 5, "scalar.txt");
    }
}
