package Task5;

public class SymbolAsync extends Thread {
    private final char s;
    public SymbolAsync(char symbol){
        s=symbol;
    }

    @Override
    public void run(){
        for(int i = 0; i< 100; i++){
            for(int j = 0; j < 100; j++){
                System.out.print(s);
            }
            System.out.println();
        }
    }
}
