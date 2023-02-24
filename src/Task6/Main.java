package Task6;

public class Main {
    public static void main(String[] args) {
        ICounter[] counter = {new AsyncCounter(), new SyncCounter(), new SyncBlockCounter(), new LockCounter()};
        int threadNum = 2;
        int countOfCalcs = 100000;
        for(ICounter count : counter) {
            try {
                testCounter(count, threadNum, countOfCalcs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void testCounter(ICounter counter, int threadNum, int countOfCalcs) {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < countOfCalcs; j++) {
                        counter.increment();
                    }
                });
            } else {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < countOfCalcs; j++) {
                        counter.decrement();
                    }
                });
            }
        }
        for (int i = 0; i < threadNum; i++)
            threads[i].start();
        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter.print();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed: " + timeElapsed + " ms");
    }
}
