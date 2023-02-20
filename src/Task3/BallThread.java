package Task3;

import java.awt.*;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            if(b.color.equals(Color.red)){
                Thread.currentThread().setPriority(MAX_PRIORITY);
            }
            else {
                Thread.currentThread().setPriority(MIN_PRIORITY);
            }
            for (int i = 1; i < 10000; i++) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(2);
            }
        } catch (InterruptedException ex) {

        }
    }
}