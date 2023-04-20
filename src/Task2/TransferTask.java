package Task2;

import java.util.concurrent.RecursiveAction;

public class TransferTask extends RecursiveAction {
    private static final int REPS = 1000;

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;

    public TransferTask(Bank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    protected void compute() {
        System.out.println("Transfering from account " + fromAccount);
        for (int i = 0; i < REPS; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random() / REPS);
            bank.transfer(fromAccount, toAccount, amount);
        }
    }
}
