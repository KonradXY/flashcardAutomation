package main.java.modelDecorator;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.function.Function;

public interface DecoratingCard extends IAnkiCard {

    // NB: To move quickly from a card to a decorated card the interface should have a static method which takes the
    // first and returns the latter. Since DecoratingCard extends IAnkiCard abstract methods, this is trivial.
    // Simply certe an anonymous implementation and forward all calls to the adapted component
    static DecoratingCard from(IAnkiCard card) {
        DecoratingCard adapted = new DecoratingCard() {
            @Override public IAnkiCard create() { return card.create(); }
            @Override public IAnkiCard create(String front, String back) { return card.create(front, back); }
            @Override public IAnkiCard create(Element front, Element back) { return card.create(front, back); }
            @Override public Element getFront() { return card.getFront(); }
            @Override public Element getBack() { return card.getBack(); }
        };
        return adapted;
    }

    // NB: funzione che mi permette di decorare gli oggetti in maniera semplice
    default DecoratingCard decorate(Function<? super DecoratingCard, ? extends DecoratingCard> decorator) {
        return decorator.apply(this);
    }

    // NB: decorator implementati a mano (non so fino a che punto uesto possa essere corretto. Rivedere con l'esempio)
    default DecoratingCard applyLeftFormatRecursively(IAnkiCard card) {
        DecoratingCard decorated = DecoratingCard.from(card);
//                .decorate(card -> new StandardFormatCardDecorator(card));
        return decorated;
    }


}
