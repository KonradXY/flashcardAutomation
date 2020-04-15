package main.java.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.strategy.TextParsingStrategy;

public class DefaultParser implements IParser {

    @Override
    public AnkiDeck parse(Path fileName, String input) {
        AnkiDeck deck = new AnkiDeck();
        TextParsingStrategy.SIMPLE_PARSER.parseFile(deck, input);
        return deck;
    }

    @Override
    public Map<Path, AnkiDeck> sort(Map<Path, AnkiDeck> mapContent) {
        return mapContent;
    }

}
