package main.java.model.parsers;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import main.java.model.deck.AnkiDeck;

public class DefaultParser implements IParser {

    @Override
    public List<AnkiDeck> parse(Path fileName, String input, String destFolder) {
        AnkiDeck deck = new AnkiDeck.Builder()
        		.withTitle(getParsedFileName(fileName))
        		.withDestFolder(destFolder)
        		.build();
        TextParsingStrategy.Q_A_PARSER.parseFile(deck, input);
        return Arrays.asList(deck);
    }
    
}
