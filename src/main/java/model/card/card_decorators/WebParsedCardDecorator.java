package main.java.model.card.card_decorators;

import main.java.model.card.IAnkiCard;

public class WebParsedCardDecorator extends LeftFormatDecorator {

	public IAnkiCard create(String word, String traduzione, String contenuto) {
        return this.createWebParsedCard(word, traduzione, contenuto);
	}

	private IAnkiCard createWebParsedCard(String word, String traduzione, String contenuto) {
        this.card.create();
        addContentToFront(word, getBoldParagraphTag().addClass("wordLearned"));
        addContentToFront(traduzione, getParagraphTag().addClass("traduzione"));
        addContentToBack(contenuto, getParagraphTag().addClass("contenuto"));
        return this.card;
    }



}
