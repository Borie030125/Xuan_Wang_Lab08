import java.io.*;
import java.util.Map;

public class SaveTagsToFile {
    public static void saveTags(Map<String, Integer> tags, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Integer> entry : tags.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        }
    }
}
