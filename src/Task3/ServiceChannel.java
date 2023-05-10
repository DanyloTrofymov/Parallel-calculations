package Task3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class ServiceChannel implements Callable<Customer> {
    private final BlockingQueue<Customer> queue;
    private final Customer customer;

    public ServiceChannel(BlockingQueue<Customer> queue, Customer customer) {
        this.queue = queue;
        this.customer = customer;
    }

    @Override
    public Customer call() {
        try {
            queue.take();
            Thread.sleep(customer.getServiceTime());
            //System.out.println("Customer " + customer.getId() + " has been served");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            return customer;
        }
    }
}
