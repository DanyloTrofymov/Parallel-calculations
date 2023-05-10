package Task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiChannelSystem extends Thread {
    public static int idCounter = 0;
    private int id;
    private static final int SERVICE_CHANNELS = 8;
    private static final int QUEUE_SIZE = 10;
    private static final int SERVICE_TIME_MIN = 500;
    private static final int SERVICE_TIME_MAX = 1500;
    private static final int ARRIVAL_TIME_MIN = 50;
    private static final int ARRIVAL_TIME_MAX = 150;
    private Result result = new Result();
    public MultiChannelSystem(){
        this.id = ++idCounter;
    }
    @Override
    public void run() {
        int totalFailedCustomers = 0;
        int totalServedCustomers = 0;
        int totalServiceTime = 0;
        int totalQueueLength = 0;
        try (ExecutorService executorService = newFixedThreadPool(SERVICE_CHANNELS)) {
            BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
            int i = 0;
            while (true){
                Customer customer = new Customer(i + 1, getRandomNumberInRange(SERVICE_TIME_MIN, SERVICE_TIME_MAX));

                int queueLength = queue.size();
                totalQueueLength += queueLength;

                if (queueLength == QUEUE_SIZE) {
                    //System.out.println("Customer " + customer.getId() + " has left the queue due to it being full");
                    totalFailedCustomers++;
                } else {
                    queue.put(customer);
                    executorService.submit(new ServiceChannel(queue, customer));
                    totalServedCustomers++;
                    totalServiceTime += customer.getServiceTime();
                }

                result = new Result(totalServedCustomers + totalFailedCustomers, totalFailedCustomers, totalServedCustomers, totalServiceTime, totalQueueLength);
                Thread.sleep(getRandomNumberInRange(ARRIVAL_TIME_MIN, ARRIVAL_TIME_MAX));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    private int getRandomNumberInRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public Result getResult() {
        return result;
    }

    public int getSystemId() {
        return id;
    }
}