import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TagExtractorApp extends JFrame {
    private JButton selectTextFileButton, selectStopWordsFileButton, saveTagsButton;
    private JTextArea tagsArea;
    private File selectedTextFile, selectedStopWordsFile;
    private Map<String, Integer> tagsFrequency;

    public TagExtractorApp() {
        super("Tag Extractor");
        setLayout(new FlowLayout());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        selectTextFileButton = new JButton("Select Text File");
        selectStopWordsFileButton = new JButton("Select Stop Words File");
        saveTagsButton = new JButton("Save Tags");
        tagsArea = new JTextArea(20, 40);
        tagsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tagsArea);

        selectTextFileButton.addActionListener(e -> selectFile(true));
        selectStopWordsFileButton.addActionListener(e -> selectFile(false));
        saveTagsButton.addActionListener(e -> saveTagsToFile());

        add(selectTextFileButton);
        add(selectStopWordsFileButton);
        add(saveTagsButton);
        add(scrollPane);
    }

    private void selectFile(boolean isTextFile) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (isTextFile) {
                selectedTextFile = fileChooser.getSelectedFile();
                extractTags();
            } else {
                selectedStopWordsFile = fileChooser.getSelectedFile();
            }
        }
    }

    private void extractTags() {
        if (selectedTextFile == null || selectedStopWordsFile == null) {
            JOptionPane.showMessageDialog(this, "Please select both text and stop words files.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Set<String> stopWords = loadStopWords();
        tagsFrequency = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(selectedTextFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.toLowerCase().split("\\W+")) {
                    if (!word.isEmpty() && !stopWords.contains(word)) {
                        tagsFrequency.put(word, tagsFrequency.getOrDefault(word, 0) + 1);
                    }
                }
            }

            displayTags();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Set<String> loadStopWords() {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedStopWordsFile))) {
            String word;
            while ((word = reader.readLine()) != null) {
                stopWords.add(word.toLowerCase());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stopWords;
    }

    private void displayTags() {
        StringBuilder tagsBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : tagsFrequency.entrySet()) {
            tagsBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        tagsArea.setText(tagsBuilder.toString());
    }

    private void saveTagsToFile() {
        if (tagsFrequency == null || tagsFrequency.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tags to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<String, Integer> entry : tagsFrequency.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Tags saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TagExtractorApp().setVisible(true));
    }
}
