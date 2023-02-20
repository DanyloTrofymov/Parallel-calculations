package Task5;

public class SymbolSync extends Thread{
    private char s;
    private Sync sync;
    private boolean controlValue;
    public SymbolSync(char symbol, Sync permission, boolean control){
        s = symbol;
        sync = permission;
        controlValue = control;
    }

    @Override
    public void run(){
        while (true){
            sync.waitAndChange(controlValue, s);
            if(sync.isStop())
                return;

        }
    }
}
