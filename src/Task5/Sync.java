package Task5;

public class Sync {
    private boolean permission;
    private int symbols;
    private int lines;
    private boolean stop;

    public Sync() {
        permission = true;
        symbols = 0;
        lines = 0;
        stop = false;
    }

    public synchronized boolean getPermission() {
        return permission;
    }

    public synchronized boolean isStop() {
        return stop;
    }

    public synchronized void waitAndChange(boolean control, char s) {
        while (getPermission() != control) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        System.out.print(s);
        permission = !permission;
        symbols++;
        if (symbols == 100) {
            symbols = 0;
            System.out.println();
            lines++;
        }
        if (lines == 100) {
            stop = true;
        }
        notifyAll();

    }
}
