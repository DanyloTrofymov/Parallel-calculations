package Task1and3;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        File directory = new File("out/test");
        System.out.println(directory.getAbsolutePath());
        List<String> keywords = new ArrayList<>(Arrays.asList(
                "programming", "software", "hardware", "networking", "database", "encryption", "cybersecurity",
                "artificial", "intelligence", "virtual", "blockchain", "interface"));
        FileAnalyser task = new FileAnalyser(directory, keywords);
        pool.invoke(task);
        System.out.println("Average word length is: " + task.getAverageWordLength());
        System.out.println("Word length are: " + task.getWordLengths());
        System.out.println("Common words are: " + task.getCommonWords());
        System.out.println("Documents with keywords are: " + task.getDocumentsWithKeywords());
        System.out.println("Documents with keywords count: " + task.getDocumentsWithKeywords().size());
    }
}
