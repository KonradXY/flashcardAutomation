package main.java.card_decorators;

import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

import java.util.function.UnaryOperator;

public class StandardCardDecorator extends AbstractCardDecorator {

	public IAnkiCard create(Elements front, Elements back) {
		return this.createStandardFormatCard(front, back);
	}

	private IAnkiCard createStandardFormatCard(Elements front, Elements back) {
		return DecoratingCard.from(new AnkiCard(front, back)).decorateWith(leftFormatDecoration);
	}

	private static UnaryOperator<DecoratingCard> leftFormatDecoration = it -> {
		it.getFront().children().stream().forEach(applyLeftFormat);
		it.getBack().children().stream().forEach(applyLeftFormat);
		return it;
	};

	// NB: decorator implementati a mano (non so fino a che punto uesto possa essere corretto. Rivedere con l'esempio)
	public static DecoratingCard decorateWithLeftFormat(IAnkiCard card) {
		return DecoratingCard.from(card).decorateWith(leftFormatDecoration);
	}
}
