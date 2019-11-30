package main.java.modelDecorator;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.java.model.AbstractAnkiCard;

import java.util.List;
import java.util.Map;

public class CardDecorator {

    private final static Logger log = Logger.getLogger(CardDecorator.class);

    /** class attributes**/
    private static final String MARGIN = "margin", AUTO = "auto";
    private static final String ALIGN = "align", LEFT = "left";
    private static final String TEXT_ALIGN = "text-align";
    private static final String FONT_STYLE = "font style";
    private static final String FONT_SIZE = "font-size: 10pt";

    private final static Tag P_TAG      = Tag.valueOf("p");
    private final static Tag B_TAG      = Tag.valueOf("b");
    private final static Tag I_TAG      = Tag.valueOf("i");
    private final static Tag DIV_TAG    = Tag.valueOf("div");
    private final static Tag UL_TAG     = Tag.valueOf("ul");
    private final static Tag LI_TAG     = Tag.valueOf("li");
    private final static Tag NEW_LINE_TAG = Tag.valueOf("br");
    private final static Tag SPAN_TAG   = Tag.valueOf("span");

    private static Element getParagraphTag()            { return new Element(P_TAG, "");}
    private static Element getBoldParagraphTag()        { return new Element(B_TAG, "");}
    private static Element getItalicParagraphTag()      { return new Element(I_TAG, "");}
    private static Element getDivTag()                  { return new Element(DIV_TAG, "");}
    private static Element getUnorderedListTag()        { return new Element(UL_TAG, "");}// .attr("style", "list-style-type: disc;");}
    private static Element getListItemTag()             { return new Element(LI_TAG, "");}
    private static Element getNewLineTag()              { return new Element(NEW_LINE_TAG, "");}
    private static Element getSpanTag()                 { return new Element(SPAN_TAG, "");}

    /** Front **/
    private static Element getWordLearnedTag()          { return getBoldParagraphTag().addClass("wordLearned"); }
    private static Element getTraduzioneTag()           { return getParagraphTag().addClass("traduzione"); }

    /** Back **/
    private static Element getContenutoTag()            { return getParagraphTag().addClass("contenuto"); }
    private static Element getParolaTradottaTag()       { return getItalicParagraphTag().addClass("parolaTradotta"); }
    private static Element getAudioTag()                { return getDivTag().addClass("audio"); }
    private static Element getSinonimiTag()             { return getUnorderedListTag().addClass("sinonimi"); }
    private static Element getDefinizioneTag()          { return getUnorderedListTag().addClass("definizioni");}


    // TODO <-- secondo me sto facendo troppe indirezioni qua dentro. Inoltre i metodi non sono generici. Dovrei rinominarli !
    public static void addWordLearnedToFront(AbstractAnkiCard card, String text)    { addWordLearned(card.getFront(), text); }
    public static void addWordLearnedToBack(AbstractAnkiCard card, String text)    { addWordLearned(card.getBack(), text); }

    public static void addTranslationToFront(AbstractAnkiCard card, String text)    { addTranslationToCard(text, card.getFront()); }
    public static void addParoleTradotteToBack(AbstractAnkiCard card, String text)  { addParoleTradotte(text, card.getBack()); }
    public static void addContenutoToBack(AbstractAnkiCard card, String text)       { addContenutoToCard(card.getBack(), text); }
    public static void addAudioToBack(AbstractAnkiCard card, String audio)          { addAudio(audio, card.getBack()); }

    public static void addSinonimiToBack(AbstractAnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getSinonimiTag();
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyStandardFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(applyStandardFormatRecursively(getBoldParagraphTag().text("Sinonimi")));
        card.getBack().appendChild(listaSinonimi);

    }

    public static void addDefinizioneToBack(AbstractAnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getDefinizioneTag();
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyStandardFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(applyStandardFormatRecursively(getBoldParagraphTag().text("Definizioni")));
        card.getBack().appendChild(definizioniList);

    }

    public static Element createSingleDefinizione(Map.Entry<String, String> entry) {
        Element elem = getListItemTag();
        Element definition = getParagraphTag().text(entry.getKey() + ": ");

        Element example = getItalicParagraphTag().text(entry.getValue());
        Element span = getSpanTag().appendChild(example);
        definition.appendChild(span);
        elem.appendChild(definition);
        return elem;
    }



    public static void addWordLearned(Element card, String text)            { addContentToCard(card, getWordLearnedTag(), text);}
    public static void addTranslationToCard(String text, Element card)      { addContentToCard(card, getTraduzioneTag(), text); }
    public static void addAudio(String audio, Element card)                 { addContentToCard(card, getAudioTag(), audio); }
    public static void addParoleTradotte(String text, Element card)         { addContentToCard(card, applyStandardFormatRecursively(getParolaTradottaTag()), text); }
    public static void addContenutoToCard(Element card, String text)        { addContentToCard(card, getContenutoTag(), text); }

    private static void addContentToCard(Element card, Element contentDiv, String contentText) {
        contentDiv.text(contentText);
        applyCenterFormat(contentDiv);
        card.appendChild(contentDiv);
    }


    /** ***********  Formattazione delle classi *********** **/

    public static Element applyStandardFormatRecursively(Element element) {
        applyStandardFormat(element);
        for (Element elem : element.children())
            applyStandardFormatRecursively(elem);
        return element;
    }


    public static Element applyStandardFormat(Element element) {
        removeStyleAttribute(element);
        return element.attr(ALIGN, LEFT)
                .attr(TEXT_ALIGN, LEFT)
                .attr(FONT_STYLE, FONT_SIZE)
                .attr(MARGIN, AUTO);
    }

    public static Element applyCenterFormat(Element element) {
        removeStyleAttribute(element);
        return element.attr(ALIGN, "center")
                .attr(TEXT_ALIGN, "center")
                .attr(FONT_STYLE, "12pt")
                .attr(MARGIN, AUTO);
    }

    private static void removeStyleAttribute(Element elem) {
        elem.removeAttr("style");
    }

}
