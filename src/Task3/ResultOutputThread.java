package Task3;

public class ResultOutputThread extends Thread {
    private MultiChannelSystem system;

    public ResultOutputThread(MultiChannelSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000); // output results every 5 seconds
                System.out.println("SYSTEM " + system.getSystemId() + ":\n" + system.getResult().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
