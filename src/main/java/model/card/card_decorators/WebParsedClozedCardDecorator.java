package main.java.model.card.card_decorators;

import com.google.inject.Singleton;

import main.java.model.card.IAnkiCard;

@Singleton
public class WebParsedClozedCardDecorator extends WebParsedCardDecorator {

	public IAnkiCard create(String clozeText, String word, String originalValue, String wordDefinition) {
		return createClozeAnkiCard(clozeText, word, originalValue, wordDefinition);
	}

	private IAnkiCard createClozeAnkiCard(String clozeText, String word, String originalValue, String wordDefinition) {
		this.card.create();
		addContentToFront(clozeText, getParagraphTag().addClass("traduzione"));

		addContentToBack(word, getParagraphTag().addClass("word"));
		addContentToBack(originalValue, getParagraphTag().addClass("contenuto"));
		addContentToBack(wordDefinition, getParagraphTag().addClass("wordDefinition"));

		return card;
	}

}
