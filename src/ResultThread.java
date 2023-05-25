public class ResultThread extends Thread {
    private final MultiChannelSystem system;

    public ResultThread(MultiChannelSystem system) {
        this.system = system;
    }
    Result result = new Result();
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100); // get results every 0.1 seconds
                result = system.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Result getResult() {
        return result;
    }
}
