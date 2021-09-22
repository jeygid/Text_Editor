package editor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextEditor extends JFrame {

    public JTextComponent textArea;

    public TextEditor() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setTitle("Text Editor");
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JTextField filePath = new JTextField();
        filePath.setName("FilenameField");
        filePath.setText("");
        filePath.setBounds(20,20, 400, 30);
        add(filePath);

        JButton saveButton = new JButton();
        saveButton.setName("SaveButton");
        saveButton.setText("Save");
        saveButton.setBounds(440, 20, 100, 30);
        add(saveButton);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File(filePath.getText());

                try (FileWriter fileWriter = new FileWriter(file)) {

                    fileWriter.write(textArea.getText());
                    fileWriter.flush();

                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });

        JButton loadButton = new JButton();
        loadButton.setName("LoadButton");
        loadButton.setText("Load");
        loadButton.setBounds(550, 20, 100, 30);
        add(loadButton);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File(filePath.getText());

                String fileText = "";

                try {
                    fileText = Files.readString(Paths.get(file.getPath()));
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

              textArea.setText(fileText);

            }
        });

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(20, 70, 630, 550);
        add(textArea);

        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setName("ScrollPane");
        textScroll.setVisible(true);
        textScroll.setPreferredSize(new Dimension());
        textScroll.setBounds(20, 70, 630, 550);
        add(textScroll);

        JMenu menu = new JMenu();
        menu.setName("MenuFile");
        setVisible(true);
        textScroll.setBounds(20, 20, 300, 50);
        add(menu);


        setVisible(true);

    }
}
