import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final int SYSTEMS = 5;

    public static void main(String[] args) {
        ArrayList <ResultThread> resultThreads = new ArrayList<>();
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelSystem system = new MultiChannelSystem();
            system.start();
            ResultThread resultThread = new ResultThread(system);
            resultThreads.add(resultThread);
            resultThread.start();
        }

        while (true) {
            try {
                Thread.sleep(5000); // output results every 0.5 seconds
                Result averageResult = getAverageResult(resultThreads);
                System.out.println("Average result for " + SYSTEMS + " systems:");
                System.out.println(averageResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static Result getAverageResult(ArrayList<ResultThread> results){
        int totalCustomers = 0;
        int totalFailedCustomers = 0;
        int totalServedCustomers = 0;
        int averageServiceTime = 0;
        ConcurrentHashMap<Integer, Integer> totalQueueLength = new ConcurrentHashMap<>();
        for (ResultThread resultThread : results) {
            Result result = resultThread.getResult();
            totalCustomers += result.totalCustomers;
            totalFailedCustomers += result.totalFailedCustomers;
            totalServedCustomers += result.totalServedCustomers;
            averageServiceTime += result.averageServiceTime;
            for (Integer queueLength : result.totalQueueLength.keySet()) {
                totalQueueLength.put(queueLength, totalQueueLength.getOrDefault(queueLength, 0) + result.totalQueueLength.get(queueLength));
            }
        }
        totalCustomers /= SYSTEMS;
        totalFailedCustomers /= SYSTEMS;
        totalServedCustomers /= SYSTEMS;
        averageServiceTime /= SYSTEMS;
        totalQueueLength.replaceAll((l, v) -> totalQueueLength.get(l) / SYSTEMS);
        return new Result(totalCustomers, totalFailedCustomers, totalServedCustomers, averageServiceTime, totalQueueLength);
    }
}
