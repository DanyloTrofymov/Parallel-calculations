package Task5;

public class Main {
    public static void main(String[] args) {
        boolean isSync = true;
        Thread first;
        Thread second;
        if(isSync) {
            Sync permission = new Sync();
            first = new Thread(new SymbolSync('-', permission, true));
            second = new Thread(new SymbolSync('|', permission, false));
        }
        else{
            first = new Thread(new SymbolAsync('-'));
            second = new Thread(new SymbolAsync('|'));
        }
        first.start();
        second.start();
    }
}
