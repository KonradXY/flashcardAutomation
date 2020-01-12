package main.java.modelDecorator;



import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Element;

public class WebParsedCardDecorator extends StandardFormatCardDecorator {

	public IAnkiCard create(String word, String traduzione, String contenuto) {
        this.card.create();
        addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));
        addContentToFront(card, traduzione, getParagraphTag().addClass("traduzione"));
        addContentToBack(card, contenuto, getParagraphTag().addClass("contenuto"));

		return this.card;
	}



}
