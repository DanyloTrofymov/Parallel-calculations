package Task2;

import java.util.concurrent.ForkJoinPool;

public class BankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferTask task = new TransferTask(b, i, INITIAL_BALANCE);
            pool.invoke(task);
        }
    }
}
