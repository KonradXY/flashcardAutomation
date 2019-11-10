package main.java.baseModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.contracts.IParser;

/*
 * Classe utilizzata per il parsing di file csv (l'input separator e' il pipe)
 */
public class SimpleParser implements IParser {
 
	@Override
	public List<AbstractAnkiCard> parseToAnkiFlashcard(Map<Path, String> input) {
		List<AbstractAnkiCard> ankiCards = new ArrayList<>();
		for (String text : input.values()) {
			String[] splitted = text.split(PIPE_SEPARATOR);
			int length = (splitted.length%2 == 0 ? splitted.length : splitted.length -1);
			for (int i = 0; i < length; i+=2) {
				ankiCards.add(new SimpleAnkiCard(splitted[i], splitted[i+1]));
			}
		}
		return ankiCards;
	}





}
