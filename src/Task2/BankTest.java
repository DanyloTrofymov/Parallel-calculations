package Task2;

import java.util.concurrent.ForkJoinPool;

public class BankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {

        //for (int j = 0; j < 15; j++) {
            ForkJoinPool pool = new ForkJoinPool();
            Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < NACCOUNTS; i++) {
                TransferTask task = new TransferTask(b, i, INITIAL_BALANCE);
                pool.invoke(task);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time: " + (endTime - startTime));
        //}
    }
}
