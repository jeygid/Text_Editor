package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TextEditor extends JFrame {

    public JTextComponent textArea;
    public JTextField filePath;

    public TextEditor() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setTitle("Text Editor");
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        filePath = new JTextField();
        filePath.setName("FilenameField");
        filePath.setText("");
        filePath.setBounds(20, 20, 400, 30);
        add(filePath);

        JButton saveButton = new JButton();
        saveButton.setName("SaveButton");
        saveButton.setText("Save");
        saveButton.setBounds(440, 20, 50, 50);
        add(saveButton);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        System.out.println(new File(" ").getAbsolutePath());
        Icon icon = new ImageIcon("./open.png");
        JButton loadButton = new JButton(icon);
        loadButton.setName("LoadButton");
        loadButton.setBounds(550, 20, 50, 50);
        add(loadButton);
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                load();
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

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("MenuLoad");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setVisible(true);

    }



    public void load() {

        File file = new File(filePath.getText());

        String fileText = "";

        try {
            fileText = Files.readString(Paths.get(file.getPath()));
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        textArea.setText(fileText);

    }

    public void save() {

        File file = new File(filePath.getText());

        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(textArea.getText());
            fileWriter.flush();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


}
