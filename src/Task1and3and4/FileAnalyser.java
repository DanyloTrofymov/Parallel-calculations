package Task1and3and4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class FileAnalyser extends RecursiveAction {
    private final File directory;
    private static Map<Integer, Integer> wordLengths = new HashMap<>();
    private static Set<String> commonWords = new HashSet<>();
    private List<String> keywords;
    private static Set<String> documentsWithKeywords = new HashSet<>();

    public FileAnalyser(File directory, List<String> keywords) {
        this.directory = directory;
        keywords.replaceAll(String::toLowerCase);
        this.keywords = keywords;
    }

    @Override
    protected void compute() {
        List<FileAnalyser> subTasks = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                FileAnalyser subTask = new FileAnalyser(file, keywords);
                subTasks.add(subTask);
                subTask.fork();
            } else {
                //System.out.println("Analyzing file: " + file.getAbsolutePath());
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    Set<String> setWords = new HashSet<>();
                    while ((line = reader.readLine()) != null) {
                        List<String> words = getWords(line);

                        processWordLength(words);
                        setWords.addAll(words);
                    }

                    processCommonWords(setWords);

                    if(!keywords.isEmpty()) {
                        processKeywords(setWords, file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (FileAnalyser subTask : subTasks) {
            subTask.join();
        }
    }

    private List<String> getWords(String line){
        String[] words = line.split("\\s+");
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (word.matches("\\p{L}+")) {
                filteredWords.add(word.toLowerCase());
            }
        }
        return filteredWords;
    }

    private void processKeywords(Set<String> setWords, File file){
        setWords.retainAll(keywords);
        if(!setWords.isEmpty()){
            documentsWithKeywords.add(file.getPath());
            //System.out.println(setWords + " " + file.getPath());
        }
    }
    private void processCommonWords(Set<String> words) {
        words.removeIf(word -> !word.matches("\\p{L}+"));
        if (commonWords.isEmpty()) {
            commonWords.addAll(words);
        }
        else{
            commonWords.retainAll(words);
        }
    }
    private void processWordLength(List<String> words) {
        for (String word : words) {
            int length = word.length();
            if (wordLengths.containsKey(length)) {
                wordLengths.put(length, wordLengths.get(length) + 1);
            } else {
                wordLengths.put(length, 1);
            }
        }
    }
    public double getAverageWordLength() {
        int sum = 0;
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            sum += entry.getKey() * entry.getValue();
            count += entry.getValue();
        }
        System.out.println("sum = " + sum);
        System.out.println("count = " + count);
        double average = (double) sum / count;
        return Math.round(average * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public Map<Integer, Integer> getWordLengths() {
        return wordLengths;
    }
    public Set<String> getCommonWords() {
        return commonWords;
    }
    public Set<String> getDocumentsWithKeywords(){
        return documentsWithKeywords;
    }
}