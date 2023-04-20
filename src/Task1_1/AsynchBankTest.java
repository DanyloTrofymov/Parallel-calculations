package Task1_1;

import java.util.ArrayList;
import java.util.List;

public class AsynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < 20; j++) {
            Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
            List<TransferThread> threads = new ArrayList<>();
            for (int i = 0; i < NACCOUNTS; i++) {
                TransferThread t = new TransferThread(b, i,
                        INITIAL_BALANCE);
                t.setPriority(Thread.NORM_PRIORITY + i % 2);
                t.start();
                threads.add(t);
            }
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime));
    }
}