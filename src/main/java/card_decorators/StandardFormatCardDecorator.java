package main.java.card_decorators;

import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

public class StandardFormatCardDecorator extends AbstractCardDecorator {

	public IAnkiCard create(Elements front, Elements back) {
		return this.createStandardFormatCard(front, back);
	}

	private IAnkiCard createStandardFormatCard(Elements front, Elements back) {
		front.stream().forEach(applyLeftFormat);
		back.stream().forEach(applyLeftFormat);
		return new AnkiCard(front, back);
	}

}
