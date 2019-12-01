package main.java.modelDecorator;

import java.util.Arrays;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

public class AbstractCardDecorator {
	
	protected IAnkiCard card;
	
	public IAnkiCard create() {
		return card.create();
	}
	public IAnkiCard create(String front, String back) {
		return card.create(front, back);
	}
	public IAnkiCard create(Element front, Element back) {
		return card.create(front, back);
	}
	
	public void setCard(IAnkiCard card) {
		this.card = card;
	}

    /** class attributes**/
    protected static final String MARGIN = "margin", AUTO = "auto";
    protected static final String ALIGN = "align";
	protected static final String LEFT = "left";
    protected static final String TEXT_ALIGN = "text-align";
    protected static final String FONT_STYLE = "font style";
    protected static final String FONT_SIZE = "font-size: 10pt";

    protected final static Tag P_TAG      = Tag.valueOf("p");
    protected final static Tag B_TAG      = Tag.valueOf("b");
    protected final static Tag I_TAG      = Tag.valueOf("i");
    protected final static Tag DIV_TAG    = Tag.valueOf("div");
    protected final static Tag UL_TAG     = Tag.valueOf("ul");
    protected final static Tag LI_TAG     = Tag.valueOf("li");
    protected final static Tag NEW_LINE_TAG = Tag.valueOf("br");
    protected final static Tag SPAN_TAG   = Tag.valueOf("span");

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
    public static void addParoleTradotte(String text, Element card)         { addContentToCard(card, applyStandardFormatRecursively(getParolaTradottaTag()), text); }
    public static void addContenutoToCard(Element card, String text)        { addContentToCard(card, getContenutoTag(), text); }

    private static void addContentToCard(Element card, Element contentDiv, String contentText) {
        contentDiv.text(contentText);
        applyCenterFormat(contentDiv);
        card.appendChild(contentDiv);
    }


    /** ***********  Formattazione delle classi *********** **/
    protected static Element applyStandardFormat(Element element) {
        removeStyleAttribute(element);
        return element.attr(ALIGN, LEFT)
                .attr(TEXT_ALIGN, LEFT)
                .attr(FONT_STYLE, FONT_SIZE)
                .attr(MARGIN, AUTO);
    }
	
	protected static Element applyStandardFormatRecursively(Element element) {
        applyStandardFormat(element);
        for (Element elem : element.children())
            applyStandardFormatRecursively(elem);
        return element;
    }

    public static Element applyCenterFormat(Element element) {
        removeStyleAttribute(element);
        return element.attr(ALIGN, "center")
                .attr(TEXT_ALIGN, "center")
                .attr(FONT_STYLE, "12pt")
                .attr(MARGIN, AUTO);
    }

    protected static void removeStyleAttribute(Element elem) {
        removeUselessAttributes(elem, "style");
    }
    
    private static void removeUselessAttributes(Element elem, String... attrs) {
    	for (String attr : Arrays.asList(attrs))
    		elem.removeAttr(attr);
    }

}
