import java.io.*;
import java.util.*;

public class TagExtractor {
    public static Map<String, Integer> extractTags(File textFile, Set<String> stopWords) throws IOException {
        Map<String, Integer> wordFrequency = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split("\\s+")) {
                    word = word.toLowerCase().replaceAll("[^a-z]", "");
                    if (!word.isEmpty() && !stopWords.contains(word)) {
                        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }
        return wordFrequency;
    }

    public static void main(String[] args) throws IOException {
        // Example usage
        File textFile = new File("path/to/text/file.txt");
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "to", "is", "are")); // This should be loaded from a file
        Map<String, Integer> tags = extractTags(textFile, stopWords);
        // Print tags and frequencies for testing
        for (Map.Entry<String, Integer> entry : tags.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
