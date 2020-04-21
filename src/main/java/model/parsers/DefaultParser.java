package main.java.model.parsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiDeck;
import main.java.model.AnkiDeck.Builder;
import main.java.strategy.TextParsingStrategy;
import main.java.utils.Property;

public class DefaultParser implements IParser {

    @Override
    public List<AnkiDeck> parse(Path fileName, String input, String destFolder) {
        AnkiDeck deck = new AnkiDeck.Builder()
        		.withTitle(getParsedFileName(fileName))
        		.withDestFolder(destFolder)
        		.build();
        TextParsingStrategy.SIMPLE_PARSER.parseFile(deck, input);
        return Arrays.asList(deck);
    }
    
}
