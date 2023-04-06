package Task1Testing;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AsynchBankTest {
    public static final int[] NACCOUNTS_ARR = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    public static final int[] REPS_ARR = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

    public static final int[][] sumsOfBanks = new int[NACCOUNTS_ARR.length][REPS_ARR.length];
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        for(int i = 0; i < REPS_ARR.length; i++) {
            for (int j = 0; j < NACCOUNTS_ARR.length; j++) {
                List<TransferThread> threads = new ArrayList<>();
                Bank b = new Bank(NACCOUNTS_ARR[j], INITIAL_BALANCE);
                int k;
                for (k = 0; k < NACCOUNTS_ARR[j]; k++) {
                    TransferThread t = new TransferThread(b, k,
                            INITIAL_BALANCE, REPS_ARR[i]);
                    t.setPriority(Thread.NORM_PRIORITY + k % 2);
                    threads.add(t);
                    t.start();
                }
                try {
                    for (TransferThread t : threads) {
                        t.join(); // блокуємо потік до завершення всіх TransferThread
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b.test(REPS_ARR[i]);
                sumsOfBanks[j][i] = b.getSum();
            }
        }

        try {
            File file = new File("result.txt");
            FileWriter fileWriter = new FileWriter(file);
            for(int i = 0; i < REPS_ARR.length; i++) {
                for (int j = 0; j < NACCOUNTS_ARR.length; j++) {
                    fileWriter.write(sumsOfBanks[i][j] + ",");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}