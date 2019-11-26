package main.java.evernoteModel;

import main.java.baseModel.AbstractAnkiCard;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import static main.java.contracts.IParser.NEW_LINE;
import static main.java.contracts.IParser.TAB;

public class EvernoteAnkiCard extends AbstractAnkiCard {

    private static final Logger log = Logger.getLogger(EvernoteAnkiCard.class);

    public EvernoteAnkiCard() {
        super();
    }

    public EvernoteAnkiCard(String front, String back) {
        this.frontHtml = new Element(divTag, "").addClass(FRONT).append(front);
        this.backHtml = new Element(divTag, "").addClass(BACK).append(back);
    }

    @Override
    public String toString() {
        return frontHtml.toString().replace("\n", "") + TAB
                + backHtml.toString().replace("\n", "") + NEW_LINE;
    }


}
