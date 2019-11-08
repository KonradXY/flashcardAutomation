package main.java.baseModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.contracts.IParser;

public class SimpleParser implements IParser {
 
	@Override
	public List<AbstractAnkiCard> parseToAnkiFlashcard(Map<Path, String> input) {
		List<AbstractAnkiCard> ankiCards = new ArrayList<>();
		for (String text : input.values()) {
			String[] splitted = text.split(INPUT_SEPARATOR);
			int length = (splitted.length%2 == 0 ? splitted.length : splitted.length -1);
			for (int i = 0; i < length; i+=2) {
				ankiCards.add(new SimpleAnkiCard(splitted[i], splitted[i+1]));
			}
		}
		return ankiCards;
	}





}
