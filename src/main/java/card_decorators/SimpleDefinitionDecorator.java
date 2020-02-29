package main.java.card_decorators;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Element;

import java.util.function.UnaryOperator;

public class SimpleDefinitionDecorator extends AbstractCardDecorator {

    public IAnkiCard decorate(IAnkiCard card) {
        return card;
    }

    // TODO - credo che qua la funzione debba avere i front e back e nn la card (anche perche' una volta creta la card nn posso piu' tornare indietro praticamente.
    public static DecoratingCard decorateSimpleDefinitionCard(IAnkiCard card) {
        return DecoratingCard.from(card).decorateWith(decorateSimpleDefinitionCard);
    }

    static UnaryOperator<DecoratingCard> decorateSimpleDefinitionCard = it -> {
        Element back = it.getBack();
        String textBack = back.html();
        textBack = formatSimpleDefinitionEntry(textBack);

        return it;
    };

    private static String formatSimpleDefinitionEntry(String entry) {
        return entry;
    }




}
