//package main.java.model.evernote;
//
//import org.apache.log4j.Logger;
//import org.jsoup.nodes.Element;
//
//import main.java.model.AbstractAnkiCard;
//
//import static main.java.contracts.IParser.NEW_LINE;
//import static main.java.contracts.IParser.TAB;
//
//public class EvernoteAnkiCard extends AbstractAnkiCard {
//
//    private static final Logger log = Logger.getLogger(EvernoteAnkiCard.class);
//
//    public EvernoteAnkiCard() {
//        super();
//    }
//
//    public EvernoteAnkiCard(String front, String back) {
//        this.front = new Element(divTag, "").addClass(FRONT).append(front);
//        this.back = new Element(divTag, "").addClass(BACK).append(back);
//    }
//
//    @Override
//    public String toString() {
//        return front.toString().replace("\n", "") + TAB
//                + back.toString().replace("\n", "") + NEW_LINE;
//    }
//
//
//}
