package main.java.modelDecorator;

import static main.java.modelDecorator.DecoratingCard.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.jsoup.nodes.Element;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;
import org.jsoup.parser.Tag;

public class AbstractCardDecorator implements DecoratingCard {

	protected IAnkiCard card;
    public void setCard(IAnkiCard card) {
        this.card = card;
    }

    @Override public IAnkiCard create() {
		return card.create();
	}
    @Override public IAnkiCard create(String front, String back) {
		return card.create(front, back);
	}
    @Override public IAnkiCard create(Element front, Element back) {
		return card.create(front, back);
	}
    @Override public Element getFront() {return card.getFront();}
    @Override public Element getBack() {return card.getBack();}

    /** class attributes**/
    protected static String MARGIN = "margin", AUTO = "auto";
    protected static String ALIGN = "align";
    protected static String LEFT = "left";
    protected static String CENTER = "center";
    protected static String TEXT_ALIGN = "text-align";
    protected static String FONT_STYLE = "font style";
    protected static String FONT_SIZE_10 = "font-size: 10pt";
    protected static String FONT_SIZE_12 = "font-size: 12pt";

    protected static Tag P_TAG = Tag.valueOf("p");
    protected static Tag B_TAG = Tag.valueOf("b");
    protected static Tag I_TAG = Tag.valueOf("i");
    protected static Tag DIV_TAG = Tag.valueOf("div");
    protected static Tag UL_TAG = Tag.valueOf("ul");
    protected static Tag LI_TAG = Tag.valueOf("li");
    protected static Tag NEW_LINE_TAG = Tag.valueOf("br");
    protected static Tag SPAN_TAG = Tag.valueOf("span");

    protected static Element getParagraphTag()            { return new Element(P_TAG, "");}
    protected static Element getBoldParagraphTag()        { return new Element(B_TAG, "");}
    protected static Element getItalicParagraphTag()      { return new Element(I_TAG, "");}
    protected static Element getDivTag()                  { return new Element(DIV_TAG, "");}
    protected static Element getListItemTag()             { return new Element(LI_TAG, "");}
    protected static Element getNewLineTag()              { return new Element(NEW_LINE_TAG, "");}
    protected static Element getSpanTag()                 { return new Element(SPAN_TAG, "");}

    /** Front **/
    protected static Element getWordLearnedTag()          { return getBoldParagraphTag().addClass("wordLearned"); }
    protected static Element getTraduzioneTag()           { return getParagraphTag().addClass("traduzione"); }
    /** Back **/
    protected static Element getContenutoTag()            { return getParagraphTag().addClass("contenuto"); }
    protected static Element getParolaTradottaTag()       { return getItalicParagraphTag().addClass("parolaTradotta"); }
    protected static Element getUnorderedListTag()        { return new Element(UL_TAG, ""); }


    // TODO <-- secondo me sto facendo troppe indirezioni qua dentro. Inoltre i metodi non sono generici. Dovrei rinominarli !
    // TODO <-- inoltre dovrei lavorare per interfacce mentre qua sto lavorando per una implementazione delle carte. Dovrei strutturare molto meglio questa classe (vedere di trovare una soluzione elegante x sta roba)
    // TODO <-- piuttosto che passare la carta dovrei utilizzare il this qui dentro !
    public static void addWordLearnedToFront(IAnkiCard card, String text)    { addWordLearned(card.getFront(), text); }
    public static void addWordLearnedToBack(IAnkiCard card, String text)     { addWordLearned(card.getBack(), text); }
    public static void addTranslationToFront(IAnkiCard card, String text)    { addTranslationToCard(text, card.getFront()); }
    public static void addParoleTradotteToBack(IAnkiCard card, String text)  { addParoleTradotte(text, card.getBack()); }
    public static void addContenutoToBack(IAnkiCard card, String text)       { addContenutoToCard(card.getBack(), text); }

    public static void addWordLearnedToFront(AnkiCard card, String text)    { addWordLearned(card.getFront(), text); }
    public static void addWordLearnedToBack(AnkiCard card, String text)     { addWordLearned(card.getBack(), text); }
    public static void addTranslationToFront(AnkiCard card, String text)    { addTranslationToCard(text, card.getFront()); }
    public static void addParoleTradotteToBack(AnkiCard card, String text)  { addParoleTradotte(text, card.getBack()); }
    public static void addContenutoToBack(AnkiCard card, String text)       { addContenutoToCard(card.getBack(), text); }

    // TODO <<--- Queste sono le indirezioni
    public static void addWordLearned(Element card, String text)            { addContentToCard(card, getWordLearnedTag(), text);}
    public static void addTranslationToCard(String text, Element card)      { addContentToCard(card, getTraduzioneTag(), text); }
    public static void addParoleTradotte(String text, Element card)         { addContentToCard(card, getParolaTradottaTag(), text); }
    public static void addContenutoToCard(Element card, String text)        { addContentToCard(card, getContenutoTag(), text); }

    // TODO - mentre questa e' la funzione
    private static void addContentToCard(Element card, Element contentDiv, String contentText) {
        contentDiv.text(contentText);
        applyCenterFormat(contentDiv);
        card.appendChild(contentDiv);
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


    /** ***********  Formattazione delle classi css *********** **/
	protected static void applyStandardFormatRecursively(Element element) {
        applyLeftFormat(element);
        element.children().stream().forEach(AbstractCardDecorator::applyLeftFormat);
    }

    protected static void applyFormatRecursively(Consumer<Element> format, Element elem) {
	    format.accept(elem);
	    elem.children().stream().forEach(format);
    }

    protected static Consumer<Element> applyLeftFormat = AbstractCardDecorator::applyLeftFormat;
    protected static Consumer<Element> applyCenterFormat = AbstractCardDecorator::applyCenterFormat;

    protected static void applyLeftFormat(Element element) {
        applyFormat(element, LEFT, LEFT, FONT_SIZE_10, AUTO);
    }
    public static void applyCenterFormat(Element element) {
        applyFormat(element, CENTER, CENTER, FONT_SIZE_12, AUTO);
    }

    public static void applyFormat(Element element, String align, String textAlign, String fontSize, String margin) {
        removeStyleAttribute(element);
        element.attr(ALIGN, align)
                .attr(TEXT_ALIGN, textAlign)
                .attr(FONT_STYLE, fontSize)
                .attr(MARGIN, margin);
    }

    protected static void removeStyleAttribute(Element elem) {
        removeUselessAttributes(elem, "style");
    }
    
    private static void removeUselessAttributes(Element elem, String... attrs) {
    	for (String attr : Arrays.asList(attrs))
    		elem.removeAttr(attr);
    }

}
