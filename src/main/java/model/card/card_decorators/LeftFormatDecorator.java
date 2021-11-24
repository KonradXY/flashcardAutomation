package main.java.model.card.card_decorators;

import java.util.function.UnaryOperator;

import org.jsoup.select.Elements;

import main.java.model.card.IAnkiCard;
import main.java.model.card.AnkiCard;

public class LeftFormatDecorator extends AbstractCardDecorator {

	public IAnkiCard create(Elements front, Elements back) {
		return this.createDecoratedCard(front, back);
	}

	private IAnkiCard createDecoratedCard(Elements front, Elements back) {
		return DecoratingCard.from(new AnkiCard(front, back)).decorateWith(leftFormatDecoration);
	}

	private static UnaryOperator<DecoratingCard> leftFormatDecoration = it -> {
		it.getFront().children().stream().forEach(applyLeftFormat);
		it.getBack().children().stream().forEach(applyLeftFormat);
		return it;
	};

	// NB: decorator implementati a mano (non so fino a che punto questo possa essere corretto. Rivedere con l'esempio)
	public static DecoratingCard decorateWithLeftFormat(IAnkiCard card) {
		return DecoratingCard.from(card).decorateWith(leftFormatDecoration);
	}
}
