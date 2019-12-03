package main.java.modelDecorator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

public class AbstractCardDecorator {
	
	protected IAnkiCard card;
	
	protected IAnkiCard create() {
		return card.create();
	}
    protected IAnkiCard create(String front, String back) {
		return card.create(front, back);
	}
    protected IAnkiCard create(Element front, Element back) {
		return card.create(front, back);
	}

    public void setCard(IAnkiCard card) {
		this.card = card;
	}

    /** class attributes**/
    private static final String MARGIN = "margin", AUTO = "auto";
    private static final String ALIGN = "align";
	private static final String LEFT = "left";
	private static final String CENTER = "center";
    private static final String TEXT_ALIGN = "text-align";
    private static final String FONT_STYLE = "font style";
    private static final String FONT_SIZE_10 = "font-size: 10pt";
    private static final String FONT_SIZE_12 = "font-size: 12pt";

    private static final Tag P_TAG      = Tag.valueOf("p");
    private static final Tag B_TAG      = Tag.valueOf("b");
    private static final Tag I_TAG      = Tag.valueOf("i");
    private static final Tag DIV_TAG    = Tag.valueOf("div");
    private static final Tag UL_TAG     = Tag.valueOf("ul");
    private static final Tag LI_TAG     = Tag.valueOf("li");
    private static final Tag NEW_LINE_TAG = Tag.valueOf("br");
    private static final Tag SPAN_TAG   = Tag.valueOf("span");

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
    protected static Element getAudioTag()                { return getDivTag().addClass("audio"); }

    protected static Element getUnorderedListTag() {
		return new Element(UL_TAG, ""); // .attr("style", "list-style-type: disc;");}
	}


    // TODO <-- secondo me sto facendo troppe indirezioni qua dentro. Inoltre i metodi non sono generici. Dovrei rinominarli !
    public static void addWordLearnedToFront(AnkiCard card, String text)    { addWordLearned(card.getFront(), text); }
    public static void addWordLearnedToBack(AnkiCard card, String text)    { addWordLearned(card.getBack(), text); }

    public static void addTranslationToFront(AnkiCard card, String text)    { addTranslationToCard(text, card.getFront()); }
    public static void addParoleTradotteToBack(AnkiCard card, String text)  { addParoleTradotte(text, card.getBack()); }
    public static void addContenutoToBack(AnkiCard card, String text)       { addContenutoToCard(card.getBack(), text); }
    public static void addAudioToBack(AnkiCard card, String audio)          { addAudio(audio, card.getBack()); }

    
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
    public static void addParoleTradotte(String text, Element card)         { addContentToCard(card, getParolaTradottaTag(), text); }
    public static void addContenutoToCard(Element card, String text)        { addContentToCard(card, getContenutoTag(), text); }

    private static void addContentToCard(Element card, Element contentDiv, String contentText) {
        contentDiv.text(contentText);
        applyCenterFormat(contentDiv);
        card.appendChild(contentDiv);
    }

    /** ***********  Formattazione delle classi *********** **/



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
