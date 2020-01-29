package main.java.modelDecorator;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Element;

import java.util.function.Function;

public interface DecoratingCard extends IAnkiCard {

    // NB: To move quickly from a card to a decorated card the interface should have a static method which takes the
    // first and returns the latter. Since DecoratingCard extends IAnkiCard abstract methods, this is trivial.
    // Simply create an anonymous implementation and forward all calls to the adapted component
    // NB: il problema qua e' che se aggiungo roba sull'IAnkiCard devo trasferirla anche qui !!!

    static DecoratingCard from(IAnkiCard card) {
        DecoratingCard adapted = new DecoratingCard() {
            @Override public IAnkiCard create() { return card.create(); }
            @Override public IAnkiCard create(String front, String back) { return card.create(front, back); }
            @Override public IAnkiCard create(Element front, Element back) { return card.create(front, back); }
            @Override public Element getFront() { return card.getFront(); }
            @Override public Element getBack() { return card.getBack(); }
            @Override public String toString() {return card.toString();}
        };
        return adapted;
    }

    // NB: funzione che mi permette di decorare gli oggetti in maniera semplice
    default DecoratingCard decorateWith(Function<? super DecoratingCard, ? extends DecoratingCard> decorator) {
        return decorator.apply(this);
    }

    // NB: se nn ho accesso al decorator ma solo alla carta posso usare questa funzione:
    default DecoratingCard decorateFromCard(Function<? super DecoratingCard, ? extends IAnkiCard> decorator ) {
        IAnkiCard decoratedCard = decorator.apply(this);
        return from(decoratedCard);
    }

    // NB: decorator implementati a mano (non so fino a che punto uesto possa essere corretto. Rivedere con l'esempio)
    static DecoratingCard decorateWithLeftFormat(IAnkiCard card) {
        DecoratingCard decorated = DecoratingCard.from(card)
                                        .decorateWith(StandardFormatCardDecorator.decorateWithLeftFormat);
        return decorated;
    }

    // TODO - probabilmente dovrei unificare il contratto legato all'utilizzo dei decorator. Il problema e' che questi decorator si comportano in maniera diversa
    // TODO - il cloze  ad esempio utilizza dati diversi nella firma. Verificare la maniera migliore per andare avanti con i decorator !!

}
