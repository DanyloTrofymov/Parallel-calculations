package Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int SYSTEMS = 100;

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(SYSTEMS);
        List<Callable<Object>> todo = new ArrayList<>();
        List<MultiChannelSystem> systems = new ArrayList<>();
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelSystem system = new MultiChannelSystem();
            systems.add(system);
            Thread thread = new Thread(system);
            todo.add(Executors.callable(thread));
        }
        try {
            executorService.invokeAll(todo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

        double totalCustomers = 0;
        double totalFailedCustomers = 0;
        double totalServedCustomers = 0;
        double averageServiceTime = 0;
        double averageQueueLength = 0;
        double failureRate = 0;
        for (MultiChannelSystem system : systems) {
            totalCustomers += system.getResult().totalCustomers;
            totalFailedCustomers += system.getResult().totalFailedCustomers;
            totalServedCustomers += system.getResult().totalServedCustomers;
            averageServiceTime += system.getResult().averageServiceTime;
            averageQueueLength += system.getResult().averageQueueLength;
            failureRate += system.getResult().failureRate;
        }
        totalCustomers/= SYSTEMS;
        totalFailedCustomers/= SYSTEMS;
        totalServedCustomers/= SYSTEMS;
        averageServiceTime/= SYSTEMS;
        averageQueueLength/= SYSTEMS;
        failureRate/= SYSTEMS;
        System.out.println("Average Results for "+ SYSTEMS + " systems :");
        String result = """
                Total Customers: %f
                Total Failed Customers: %f
                Total Served Customers: %f
                Average Service Time: %f
                Average Queue Length: %f
                Failure Probability: %.2f%%
                """.formatted(totalCustomers, totalFailedCustomers, totalServedCustomers, averageServiceTime, averageQueueLength, failureRate*100);
        System.out.println(result);
    }
}
