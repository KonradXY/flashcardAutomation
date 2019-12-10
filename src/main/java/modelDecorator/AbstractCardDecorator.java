package main.java.modelDecorator;

import static main.java.modelDecorator.DecoratingCard.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

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
    static String MARGIN = "margin", AUTO = "auto";
    static String ALIGN = "align";
    static String LEFT = "left";
    static String CENTER = "center";
    static String TEXT_ALIGN = "text-align";
    static String FONT_STYLE = "font style";
    static String FONT_SIZE_10 = "font-size: 10pt";
    static String FONT_SIZE_12 = "font-size: 12pt";

    static Tag P_TAG = Tag.valueOf("p");
    static Tag B_TAG = Tag.valueOf("b");
    static Tag I_TAG = Tag.valueOf("i");
    static Tag DIV_TAG = Tag.valueOf("div");
    static Tag UL_TAG = Tag.valueOf("ul");
    static Tag LI_TAG = Tag.valueOf("li");
    static Tag NEW_LINE_TAG = Tag.valueOf("br");
    static Tag SPAN_TAG = Tag.valueOf("span");

    static Element getParagraphTag()            { return new Element(P_TAG, "");}
    static Element getBoldParagraphTag()        { return new Element(B_TAG, "");}
    static Element getItalicParagraphTag()      { return new Element(I_TAG, "");}
    static Element getDivTag()                  { return new Element(DIV_TAG, "");}
    static Element getListItemTag()             { return new Element(LI_TAG, "");}
    static Element getNewLineTag()              { return new Element(NEW_LINE_TAG, "");}
    static Element getSpanTag()                 { return new Element(SPAN_TAG, "");}
    static Element getUnorderedListTag()        { return new Element(UL_TAG, ""); }


    protected static void addContentToFront(IAnkiCard card, String content, Element contentDiv) {
        contentDiv.text(content);
        card.getFront().appendChild(contentDiv);
    }

    protected static void addContentToBack(IAnkiCard card, String content, Element contentDiv) {
        contentDiv.text(content);
        card.getFront().appendChild(contentDiv);
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

    @Override
    public String toString() {
        return card.toString();
    }

}
