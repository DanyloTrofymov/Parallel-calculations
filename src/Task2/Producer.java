package Task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        int importantInfo[] = produceData(1000);
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }

    private int[] produceData(int arraySize) {
        int[] data = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            data[i] = i;
        }
        return data;
    }
}