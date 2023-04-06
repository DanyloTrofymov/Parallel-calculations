package Task1Testing;

class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private int REPS;
    public TransferThread(Bank b, int from, int max, int reps){
        bank = b;
        fromAccount = from;
        maxAmount = max;
        REPS = reps;
    }
    @Override
    public void run(){
        while (bank.getTransacts() < 1000000) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random()/REPS);
                bank.transfer(fromAccount, toAccount, amount);
            }
        }
    }
}