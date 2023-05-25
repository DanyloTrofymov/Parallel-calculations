public class Main {
    private static final int SYSTEMS = 5;

    public static void main(String[] args){
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelSystem system = new MultiChannelSystem();
            system.start();
            ResultOutputThread resultOutputThread = new ResultOutputThread(system);
            resultOutputThread.start();
        }
    }
}
