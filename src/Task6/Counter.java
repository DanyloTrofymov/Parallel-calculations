package Task6;

public class Counter {
    private int c = 0;
    private final boolean isSync;

    public Counter(boolean isSync){
        this.isSync = isSync;
    }

    private synchronized void syncInc(){
        c++;
    }
    private synchronized void syncDec(){
        c--;
    }
    private void asyncInc(){
        c++;
    }
    private void asyncDec(){
        c--;
    }

    public void increment(){
        if(isSync)
            syncInc();
        else
            asyncInc();
    }
    public void decrement(){
        if(isSync)
            syncDec();
        else
            asyncDec();
    }
    public void print(){
        System.out.println("Counter is " + (isSync ? "sync" : "async") + ". The result is: " + c);
    }
}
