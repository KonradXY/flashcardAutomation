package main.java.modelDecorator;

import java.util.List;

import main.java.model.AnkiCard;

public class WebParsedClozedCardDecorator extends WebParsedCardDecorator {
	
	public AnkiCard create(String clozeText, String word, String originalValue, String wordDefinition, List<String> synonims) {
		return createClozeAnkiCard(clozeText, word, originalValue, wordDefinition, synonims);
	}
	
	private AnkiCard createClozeAnkiCard(String clozeText, String word, String originalValue, String wordDefinition, List<String> synonims) {
		AnkiCard card = new AnkiCard();
		addTranslationToFront(card, clozeText);
		addWordLearnedToBack(card, word);
		addContenutoToBack(card, originalValue);
		addContenutoToBack(card, wordDefinition);
		addSinonimiToBack(card, synonims);
		return card;
	}

}
