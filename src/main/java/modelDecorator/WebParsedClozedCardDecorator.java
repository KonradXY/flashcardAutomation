package main.java.modelDecorator;

import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

@Singleton
public class WebParsedClozedCardDecorator extends WebParsedCardDecorator {

	public IAnkiCard create(String clozeText, String word, String originalValue, String wordDefinition) {
		return createClozeAnkiCard(clozeText, word, originalValue, wordDefinition);
	}

	private IAnkiCard createClozeAnkiCard(String clozeText, String word, String originalValue, String wordDefinition) {
		this.card.create();
		addContentToFront(card, clozeText, getParagraphTag().addClass("traduzione"));

		addContentToBack(card, word, getParagraphTag().addClass("word"));
		addContentToBack(card, originalValue, getParagraphTag().addClass("contenuto"));
		addContentToBack(card, wordDefinition, getParagraphTag().addClass("wordDefinition"));

		return card;
	}

}
