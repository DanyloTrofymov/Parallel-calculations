package Task6;

public class AsyncCounter implements ICounter {
    private int c = 0;

    public void increment(){
        c++;
    }
    public void decrement(){
        c--;
    }
    public void print(){
        System.out.println("Counter is async. The result is: " + c);
    }
}
