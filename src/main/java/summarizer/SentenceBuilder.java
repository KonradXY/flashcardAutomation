package main.java.summarizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes lines provided by FileHandler and populates lists with {@link Sentence Sentence}-objects.
 *
 * @author Piraveen
 */
public class SentenceBuilder {
    // declaring local variables
    private static final String SEPERATORS = ". ";
    private static List<String> lines;
    private static ArrayList<Sentence> sentenceObjects = new ArrayList<Sentence>();


    public SentenceBuilder(String language, String filePath) {
        // build list of Sentence objects
        getSentences(filePath);


        //FIXME: Handle empty lines/sentences/words/strings

    }

    // getter
    public static ArrayList<Sentence> getSentenceObjects() {
        return sentenceObjects;
    }

    public static List<String> getLines() {
        return lines;
    }


    /**
     * This method reads every line in the file and splits it into sentences.
     * The method is currently statically set to split for either every dot or every dot and a space using regular expression.
     */
    private ArrayList<Sentence> getSentences(String path) {

        //TODO: Sentences that end with question(?) mark or exclamation(!) mark

        lines = FileHandler.readFile(path);

        for (String line : lines) {
            //regex	- add more with | 		read more-->https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
            String[] splitLines = line.split("(?<=\\. {0,1})");
            removeBlankSpaces(splitLines);
           buildSentences(splitLines);
        }

        return sentenceObjects;
    }

    private String[] removeBlankSpaces(String[] splitLines) {
        for (int j = 0; j < splitLines.length; j++) {
            if (splitLines[j].equals(" "))
                splitLines[j] = null;
        }
        return null;
    }

    private void buildSentences(String[] splitLines) {
        for (String aSentence : splitLines) {
            if (aSentence != null) {
                sentenceObjects.add(new Sentence(aSentence));
            }
        }
    }

}

