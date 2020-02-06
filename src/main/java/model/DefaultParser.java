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
    public List<IAnkiCard> parse(Path fileName, String input) {
        List<IAnkiCard> ankiCards = new ArrayList<>();
        TextParsingStrategy.SIMPLE_PARSER.parseFile(ankiCards, input);
        return ankiCards;
    }

    @Override
    public Map<Path, List<IAnkiCard>> sort(Map<Path, List<IAnkiCard>> mapContent) {
        return mapContent;
    }

}
