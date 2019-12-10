package main.java.modelDecorator;

import java.util.List;

import main.java.model.AnkiCard;

public class WebParsedClozedCardDecorator extends WebParsedCardDecorator {
	
	public AnkiCard create(String clozeText, String word, String originalValue, String wordDefinition, List<String> synonims) {
		return createClozeAnkiCard(clozeText, word, originalValue, wordDefinition, synonims);
	}
	
	private AnkiCard createClozeAnkiCard(String clozeText, String word, String originalValue, String wordDefinition, List<String> synonims) {
		AnkiCard card = new AnkiCard();
		addContentToFront(card, clozeText, getParagraphTag().addClass("traduzione"));

		addContentToBack(card, word, getParagraphTag().addClass("word"));
		addContentToBack(card, originalValue, getParagraphTag().addClass("contenuto"));
		addContentToBack(card, wordDefinition, getParagraphTag().addClass("wordDefinition"));

		addSinonimiToBack(card, synonims);	// TODO <-- pure questo e' da rivedere !
		return card;
	}

}
