package Task5;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter(true);
        int threadNum = 2;
        int countOfCalcs = 100000;
        Thread[] threads = new Thread[threadNum];
        for(int i = 0; i < threadNum; i++){
            if(i%2 == 0) {
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < countOfCalcs; j++) {
                            counter.increment();
                        }

                    }
                });
            }
            else {
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < countOfCalcs; j++) {
                            counter.decrement();
                        }

                    }
                });
            }
        }
        for(int i = 0; i < threadNum; i++)
            threads[i].start();
        for(int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    counter.print();
    }
}
