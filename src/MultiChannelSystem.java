import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger totalFailedCustomers = new AtomicInteger(0);
    private AtomicInteger totalServedCustomers = new AtomicInteger(0);
    private AtomicInteger totalServiceTime = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, Integer> totalQueueLength = new ConcurrentHashMap<>();
    BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    public MultiChannelSystem(){
        this.id = ++idCounter;
    }
    @Override
    public void run() {

        try (ExecutorService executorService = newFixedThreadPool(SERVICE_CHANNELS)) {

            int i = 0;
            while (true){
                Customer customer = new Customer(i + 1, getRandomNumberInRange(SERVICE_TIME_MIN, SERVICE_TIME_MAX));

                if (queue.size() == QUEUE_SIZE) {
                    //System.out.println("Customer " + customer.getId() + " has left the queue due to it being full");
                    totalFailedCustomers.incrementAndGet();
                } else {
                    queue.put(customer);
                    executorService.submit(new ServiceChannel(queue, customer));
                    totalServedCustomers.incrementAndGet();
                    totalServiceTime.addAndGet(customer.getServiceTime());
                }

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
        totalQueueLength.put(queue.size(), totalQueueLength.getOrDefault(queue.size(), 0) + 1);
        return new Result(totalServedCustomers.get() + totalFailedCustomers.get(), totalFailedCustomers.get(), totalServedCustomers.get(), totalServiceTime.get(), totalQueueLength);
    }

    public int getSystemId() {
        return id;
    }
}