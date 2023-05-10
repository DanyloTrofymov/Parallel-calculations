package Task3;

public class Result {
    public int totalCustomers;
    public int totalFailedCustomers;
    public int totalServedCustomers;
    public int averageServiceTime;
    public double averageQueueLength;
    public double failureRate;
    public Result(){
        this.totalCustomers = 0;
        this.totalFailedCustomers = 0;
        this.totalServedCustomers = 0;
        this.averageServiceTime = 0;
        this.averageQueueLength = 0;
        this.failureRate = 0;
    }
    public Result(int totalCustomers, int totalFailedCustomers, int totalServedCustomers, int totalServiceTime, int totalQueueLength){
        this.totalCustomers = totalCustomers;
        this.totalFailedCustomers = totalFailedCustomers;
        this.totalServedCustomers = totalServedCustomers;
        this.averageServiceTime = totalServiceTime / totalServedCustomers;
        this.averageQueueLength = (double) totalQueueLength / totalCustomers;
        this.failureRate = (double) totalFailedCustomers / totalCustomers;
    }

    @Override
    public String toString() {
        String result = """
                Total Customers: %d
                Total Failed Customers: %d
                Total Served Customers: %d
                Average Service Time: %d
                Average Queue Length: %f
                Failure Probability: %.2f%%
                """.formatted(totalCustomers, totalFailedCustomers, totalServedCustomers, averageServiceTime, averageQueueLength, failureRate*100);
        return result;
    }
}
