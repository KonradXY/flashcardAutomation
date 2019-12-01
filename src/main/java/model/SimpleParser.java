package main.java.model;

import static main.java.utils.Property.SIMPLE_PARSER_ENG_FIELD;
import static main.java.utils.Property.SIMPLE_PARSER_ESP_FIELD;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;

/*
 * Classe utilizzata per il parsing di file csv (l'input separator e' il pipe)
 */
public class SimpleParser implements IParser {
	
	@Override	// TODO - vedere se riesco a fare qualcosa con le stream perche' e' un po' illegibile
	public List<IAnkiCard> parse(Map<Path, String> input) {
		List<IAnkiCard> ankiCards = new ArrayList<>();
		for (String text : input.values()) {
			String[] splittedText = text.split(PIPE_SEPARATOR);
			
			if (entriesAreNotEven(splittedText))
				throw new RuntimeException("Le entry non sono pari ! Controllare che ogni carta abbia due facce !");
			
			int length = (splittedText.length%2 == 0 ? splittedText.length : splittedText.length -1);
			for (int i = 0; i < length; i+=2) {
				ankiCards.add(new AnkiCard(splittedText[i+SIMPLE_PARSER_ENG_FIELD], 
												 splittedText[i+SIMPLE_PARSER_ESP_FIELD]));
			}
		}
		return ankiCards;
	}
	
	private boolean entriesAreNotEven(String[] entries) {
		return entries.length%2 != 0;
	}




}
