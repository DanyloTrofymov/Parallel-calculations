package Task1;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        File directory = new File("out/test");
        System.out.println(directory.getAbsolutePath());
        FileAnalyser task = new FileAnalyser(directory);
        pool.invoke(task);
        System.out.println("Average word length is: " + task.getAverageWordLength());
        System.out.println(task.getWordLengths());
        System.out.println(task.getCommonWords());
    }
}
