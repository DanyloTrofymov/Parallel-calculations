package Task6;

public class SyncBlockCounter implements ICounter {
    private int c = 0;

    public synchronized void increment() {
        synchronized (this) {
            c++;
        }
    }

    public synchronized void decrement() {
        synchronized (this) {
            c--;
        }
    }

    public void print() {
        System.out.println("Counter is sync block" + ". The result is: " + c);
    }
}
