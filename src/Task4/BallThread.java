package Task4;

public class BallThread extends Thread {
    private Ball b;
    private BallThread parentThread;
    public BallThread(Ball ball, BallThread parent) {
        b = ball;
        parentThread = parent;
    }

    @Override
    public void run() {
        try {
            if(parentThread != null)
                parentThread.join();
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