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
    public List<IAnkiCard> parse(Map<Path, String> input) {
        List<IAnkiCard> ankiCards = new ArrayList<>();
        for (String text : input.values()) {
            TextParsingStrategy.SIMPLE_PARSER.parseFile(ankiCards, text);
        }
        return ankiCards;
    }


}
