package Task1Testing;


class Bank {
    //public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }
    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        //if (ntransacts % NTEST == 0)
        //    test();
    }
    public void test(int reps){
        System.out.println("Bank: " + accounts.length + " reps: " + reps);
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + getSum());
        System.out.println();
    }

    public int size(){
        return accounts.length;
    }

    public long getTransacts(){
        return ntransacts;
    }

    public int getSum(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        return sum;
    }
}