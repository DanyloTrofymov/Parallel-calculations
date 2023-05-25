public class ResultOutputThread extends Thread {
    private final MultiChannelSystem system;

    public ResultOutputThread(MultiChannelSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500); // output results every 0.5 seconds
                System.out.println("SYSTEM " + system.getSystemId() + ":\n" + system.getResult().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
