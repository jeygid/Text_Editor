package editor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {

    public static JTextComponent textArea;
    public JTextField searchField;
    public JFileChooser fileChooser;
    public JCheckBox useRegEx;
    public static JLabel searchStat;

    //Change if necessary
    public static String iconsPath = "./Text Editor/task/src/editor/resources/";

    public TextEditor() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setTitle("Text Editor");
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        add(fileChooser);

        JButton openButton = new JButton();
        Icon openIcon = new ImageIcon(iconsPath + "open.png");
        openButton.setIcon(openIcon);
        openButton.setName("OpenButton");
        openButton.setBounds(20, 10, 50, 50);
        add(openButton);
        openButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        JButton saveButton = new JButton();
        Icon saveIcon = new ImageIcon(iconsPath + "save.png");
        saveButton.setIcon(saveIcon);
        saveButton.setName("SaveButton");
        saveButton.setBounds(80, 10, 50, 50);
        add(saveButton);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setText("");
        searchField.setBounds(140, 20, 250, 30);
        add(searchField);

        JButton searchButton = new JButton();
        Icon searchIcon = new ImageIcon(iconsPath + "search.png");
        searchButton.setIcon(searchIcon);
        searchButton.setName("StartSearchButton");
        searchButton.setBounds(400, 10, 50, 50);
        add(searchButton);
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Search.run(textArea.getText(), searchField.getText(), useRegEx.isSelected());
            }
        });

        JButton previousButton = new JButton();
        Icon previousIcon = new ImageIcon(iconsPath + "back.png");
        previousButton.setIcon(previousIcon);
        previousButton.setName("PreviousMatchButton");
        previousButton.setBounds(460, 10, 50, 50);
        add(previousButton);
        previousButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Search.prev();
            }
        });

        JButton nextButton = new JButton();
        Icon nextIcon = new ImageIcon(iconsPath + "forward.png");
        nextButton.setIcon(nextIcon);
        nextButton.setName("NextMatchButton");
        nextButton.setBounds(520, 10, 50, 50);
        add(nextButton);
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Search.next();
            }
        });

        useRegEx = new JCheckBox();
        useRegEx.setName("UseRegExCheckbox");
        useRegEx.setText("Use regex");
        useRegEx.setBounds(580, 10, 100, 50);
        add(useRegEx);

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(20, 70, 640, 530);
        add(textArea);

        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setName("ScrollPane");
        textScroll.setVisible(true);
        textScroll.setPreferredSize(new Dimension());
        textScroll.setBounds(20, 70, 640, 530);
        add(textScroll);

        searchStat = new JLabel();
        searchStat.setName("SearchStat");
        searchStat.setText("");
        searchStat.setBounds(20, 605, 500, 20);
        add(searchStat);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem menuStartSearchItem = new JMenuItem("Start search");
        menuStartSearchItem.setName("MenuStartSearch");
        menuStartSearchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search.run(textArea.getText(), searchField.getText(), useRegEx.isSelected());
            }
        });

        JMenuItem menuPreviousMatchItem = new JMenuItem("Previous match");
        menuPreviousMatchItem.setName("MenuPreviousMatch");
        menuPreviousMatchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search.prev();
            }
        });

        JMenuItem menuNextMatchItem = new JMenuItem("Next match");
        menuNextMatchItem.setName("MenuNextMatch");
        menuNextMatchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search.next();
            }
        });

        JMenuItem menuUseRegExp = new JMenuItem("Use regular expression");
        menuUseRegExp.setName("MenuUseRegExp");
        menuUseRegExp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!useRegEx.isSelected()) {
                    useRegEx.setSelected(true);
                } else {
                    useRegEx.setSelected(false);
                }
            }
        });

        searchMenu.add(menuStartSearchItem);
        searchMenu.add(menuPreviousMatchItem);
        searchMenu.add(menuNextMatchItem);
        searchMenu.add(menuUseRegExp);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

        setVisible(true);

    }

    public void open() {

        searchStat.setText("");

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {

            File currentFile = fileChooser.getSelectedFile();

            String fileText = "";

            try {
                fileText = Files.readString(Paths.get(currentFile.getPath()));
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            textArea.setText(fileText);
        }

    }

    public void save() {

        searchStat.setText("");

        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {

            File currentFile = fileChooser.getSelectedFile();

            try (FileWriter fileWriter = new FileWriter(currentFile)) {

                fileWriter.write(textArea.getText());
                fileWriter.flush();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

}
