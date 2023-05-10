package Task1;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiChannelSystem {
    private static final int SERVICE_CHANNELS = 8;
    private static final int QUEUE_SIZE = 10;
    private static final int TOTAL_CUSTOMERS = 100;
    private static final int SERVICE_TIME_MIN = 500;
    private static final int SERVICE_TIME_MAX = 1500;
    private static final int ARRIVAL_TIME_MIN = 50;
    private static final int ARRIVAL_TIME_MAX = 150;

    public static void main(String[] args) {
        int totalFailedCustomers = 0;
        int totalServedCustomers = 0;
        int totalServiceTime = 0;
        int totalQueueLength = 0;
        try (ExecutorService executorService = newFixedThreadPool(SERVICE_CHANNELS)) {
            BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);


            for (int i = 0; i < TOTAL_CUSTOMERS; i++) {
                Customer customer = new Customer(i + 1, getRandomNumberInRange(SERVICE_TIME_MIN, SERVICE_TIME_MAX));

                int queueLength = queue.size();
                totalQueueLength += queueLength;

                if (queueLength == QUEUE_SIZE) {
                    System.out.println("Customer " + customer.getId() + " has left the queue due to it being full");
                    totalFailedCustomers++;
                } else {
                    queue.put(customer);
                    executorService.submit(new ServiceChannel(queue, customer));
                    totalServedCustomers++;
                    totalServiceTime += customer.getServiceTime();
                }
                Thread.sleep(getRandomNumberInRange(ARRIVAL_TIME_MIN, ARRIVAL_TIME_MAX));
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            printResults(totalFailedCustomers, totalServedCustomers, totalServiceTime, totalQueueLength);
        }

    }
    private static int getRandomNumberInRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    private static void printResults(int totalFailedCustomers, int totalServedCustomers, int totalServiceTime, int totalQueueLength) {
        System.out.println("Total Customers: " + TOTAL_CUSTOMERS);
        System.out.println("Total Failed Customers: " + totalFailedCustomers);
        System.out.println("Total Served Customers: " + totalServedCustomers);
        System.out.println("Average Service Time: " + (totalServiceTime / TOTAL_CUSTOMERS));
        System.out.println("Average Queue Length: " + ((double) totalQueueLength / TOTAL_CUSTOMERS));
        System.out.println("Failure Probability: " + ((double) totalFailedCustomers / TOTAL_CUSTOMERS) * 100 + "%");
    }
}