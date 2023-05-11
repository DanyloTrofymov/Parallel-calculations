import java.util.HashMap;
import java.util.Map;

public class Result {
    public int totalCustomers;
    public int totalFailedCustomers;
    public int totalServedCustomers;
    public int averageServiceTime;
    public HashMap<Integer, Integer> totalQueueLength;
    public double failureRate;
    public Result(){
        this.totalCustomers = 0;
        this.totalFailedCustomers = 0;
        this.totalServedCustomers = 0;
        this.averageServiceTime = 0;
        this.totalQueueLength = new HashMap<>();
        this.failureRate = 0;
    }
    public Result(int totalCustomers, int totalFailedCustomers, int totalServedCustomers, int totalServiceTime, HashMap<Integer, Integer> totalQueueLength){
        this.totalCustomers = totalCustomers;
        this.totalFailedCustomers = totalFailedCustomers;
        this.totalServedCustomers = totalServedCustomers;
        this.averageServiceTime = totalServiceTime / totalServedCustomers;
        this.totalQueueLength = totalQueueLength;
        this.failureRate = (double) totalFailedCustomers / totalCustomers;
    }

    @Override
    public String toString() {
        int sum = 0;
        int count = 0;
        for (Map.Entry<Integer, Integer> entry  : totalQueueLength.entrySet()) {
            sum += entry.getKey() * entry.getValue();
            count += entry.getValue();
        }
        String result = """
                Total Customers: %d
                Total Failed Customers: %d
                Total Served Customers: %d
                Average Service Time: %d
                Average Queue Length: %f
                Failure Probability: %.2f%%
                """.formatted(totalCustomers, totalFailedCustomers, totalServedCustomers, averageServiceTime, (double) sum/count, failureRate*100);
        return result;
    }
}
