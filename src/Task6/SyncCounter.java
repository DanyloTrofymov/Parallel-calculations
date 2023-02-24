package Task6;

public class SyncCounter implements ICounter {
    private int c = 0;

    public synchronized void increment(){
        c++;
    }
    public synchronized void decrement(){
        c--;
    }
    public void print(){
        System.out.println("Counter is sync" + ". The result is: " + c);
    }
}
