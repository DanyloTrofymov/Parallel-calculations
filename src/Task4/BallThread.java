package Task4;

import java.awt.*;

public class BallThread extends Thread {
    private final Ball b;
    private final BallThread parentThread;
    public BallThread(Ball ball, BallThread parent) {
        b = ball;
        parentThread = parent;
    }

    @Override
    public void run() {
        try {
            if(parentThread != null)
                parentThread.join();
            b.color = Color.RED;
            while (true) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(2);
            }
        } catch (InterruptedException ex) {
            if(!ex.getMessage().equals("sleep interrupted"))
                System.out.println(ex);
        }
    }
}