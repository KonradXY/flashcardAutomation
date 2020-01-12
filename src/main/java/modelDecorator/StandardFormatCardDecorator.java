package main.java.modelDecorator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

import java.util.function.Function;

public class StandardFormatCardDecorator extends AbstractCardDecorator {

	public IAnkiCard create(Elements front, Elements back) {
		front.stream().forEach(applyLeftFormat);
		back.stream().forEach(applyLeftFormat);
		return new AnkiCard(front, back);
	}

	public static Function<DecoratingCard, DecoratingCard> decorateWithLeftFormat = (it) -> {
		it.getFront().children().stream().forEach(applyLeftFormat);
		it.getBack().children().stream().forEach(applyLeftFormat);
		return it;
	};


}
