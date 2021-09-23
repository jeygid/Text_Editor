package editor;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {

    public static List<SearchResult> positions = new LinkedList<>();
    public static int currentPosition = 0;

    public static void select() {

        int length = positions.get(currentPosition).getFoundText().length();
        int index = positions.get(currentPosition).getPosition();

        TextEditor.textArea.setCaretPosition(index + length);
        TextEditor.textArea.select(index, index + length);
        TextEditor.textArea.grabFocus();

        TextEditor.searchStat.setText("Found occurrences: " + positions.size() + ". Search position "
                + (currentPosition + 1) + "/" + positions.size());

    }

    public static void run(String text, String searchExp, boolean useRegEx) {

        SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws Exception {

                positions.clear();
                currentPosition = 0;
                String exp;

                if (useRegEx) {
                    exp = searchExp;
                } else {
                    exp = Pattern.quote(searchExp);
                }

                Pattern pattern = Pattern.compile(exp);
                Matcher matcher = pattern.matcher(text);


                while (matcher.find()) {
                    positions.add(new SearchResult(matcher.start(), matcher.group()));
                }

                select();

                return null;

            }
        };

        searchWorker.execute();
    }

    public static void prev() {

        if (positions.isEmpty()) return;

        currentPosition--;

        if (currentPosition < 0) {
            currentPosition = positions.size() - 1;
        }

        select();

    }

    public static void next() {

        if (positions.isEmpty()) return;

        currentPosition++;

        if (currentPosition >= positions.size()) {
            currentPosition = 0;
        }

        select();

    }

}

class SearchResult {
    int position;
    String foundText;

    public int getPosition() {
        return position;
    }

    public String getFoundText() {
        return foundText;
    }

    public SearchResult(int position, String foundText) {
        this.position = position;
        this.foundText = foundText;
    }
}
