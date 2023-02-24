package Task6;

import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements ICounter {
    public int c = 0;
    ReentrantLock locker = new ReentrantLock();

    public void increment() {
        locker.lock();
        try {
            c++;
        } finally {
            locker.unlock();
        }
    }

    public void decrement() {
        locker.lock();
        try {
            c--;
        } finally {
            locker.unlock();
        }
    }

    public void print() {
        System.out.println("Counter is lock" + ". The result is: " + c);
    }
}
