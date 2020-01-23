package main.java.model;

import static main.java.utils.Property.SIMPLE_PARSER_ENG_FIELD;
import static main.java.utils.Property.SIMPLE_PARSER_ESP_FIELD;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.strategy.TextParsingStrategy;

/*
 * Classe utilizzata per il parsing di file csv (l'input separator e' il pipe)
 */
public class DefaultParser implements IParser {
	
	@Override
	public List<IAnkiCard> parse(Map<Path, String> input) {
		List<IAnkiCard> ankiCards = new ArrayList<>();
		for (String text : input.values()) {
			TextParsingStrategy.SIMPLE_PARSER.parse(ankiCards, text);
		}
		return ankiCards;
	}
	


}
