import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TagExtractorGUI extends JFrame {
    private JButton selectTextFileButton;
    private JButton selectStopWordsFileButton;
    private JTextArea textArea;

    public TagExtractorGUI() {
        setTitle("Tag Extractor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        selectTextFileButton = new JButton("Select Text File");
        selectStopWordsFileButton = new JButton("Select Stop Words File");
        textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(selectTextFileButton);
        add(selectStopWordsFileButton);
        add(scrollPane);

        selectTextFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(TagExtractorGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textArea.setText("Selected Text File: " + selectedFile.getName());
                }
            }
        });

        selectStopWordsFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Similar to the selectTextFileButton's ActionListener
                // Implement the logic to select the stop words file
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TagExtractorGUI().setVisible(true);
            }
        });
    }
}
