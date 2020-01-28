package main.java.modelDecorator;

import java.util.List;

import main.java.model.AnkiCard;

public class
WebParsedClozedCardDecorator extends WebParsedCardDecorator {
	
	public AnkiCard create(String clozeText, String word, String originalValue, String wordDefinition) {
		return createClozeAnkiCard(clozeText, word, originalValue, wordDefinition);
	}
	
	private AnkiCard createClozeAnkiCard(String clozeText, String word, String originalValue, String wordDefinition) {
		AnkiCard card = new AnkiCard();
		addContentToFront(card, clozeText, getParagraphTag().addClass("traduzione"));

		addContentToBack(card, word, getParagraphTag().addClass("word"));
		addContentToBack(card, originalValue, getParagraphTag().addClass("contenuto"));
		addContentToBack(card, wordDefinition, getParagraphTag().addClass("wordDefinition"));

		return card;
	}

}
