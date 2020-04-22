package main.java.card_decorators;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.java.contracts.IAnkiCard;

public class AbstractCardDecorator implements DecoratingCard {

    static final String MARGIN = "margin";
    static final String AUTO = "auto";
    static final String ALIGN = "align";
    static final String LEFT = "left";
    static final String CENTER = "center";
    static final String TEXT_ALIGN = "text-align";
    static final String FONT_STYLE = "font style";
    static final String FONT_SIZE_10 = "font-size: 10pt";
    static final String FONT_SIZE_12 = "font-size: 12pt";

    static final Tag P_TAG = Tag.valueOf("p");
    static final Tag B_TAG = Tag.valueOf("b");
    static final Tag I_TAG = Tag.valueOf("i");
    static final Tag DIV_TAG = Tag.valueOf("div");
    static final Tag UL_TAG = Tag.valueOf("ul");
    static final Tag LI_TAG = Tag.valueOf("li");
    static final Tag NEW_LINE_TAG = Tag.valueOf("br");
    static final Tag SPAN_TAG = Tag.valueOf("span");

    protected static Consumer<Element> applyLeftFormat = AbstractCardDecorator::applyLeftFormatToElement;
    protected static Consumer<Element> applyCenterFormat = AbstractCardDecorator::applyCenterFormatToElement;

    protected IAnkiCard card;

    public static Element getParagraphTag() {
        return new Element(P_TAG, "");
    }

    public static Element getBoldParagraphTag() {
        return new Element(B_TAG, "");
    }

    public static Element getItalicParagraphTag() {
        return new Element(I_TAG, "");
    }

    public static Element getDivTag() {
        return new Element(DIV_TAG, "");
    }

    public static Element getListItemTag() {
        return new Element(LI_TAG, "");
    }

    public static Element getNewLineTag() {
        return new Element(NEW_LINE_TAG, "");
    }

    public static Element getSpanTag() {
        return new Element(SPAN_TAG, "");
    }

    public static Element getUnorderedListTag() {
        return new Element(UL_TAG, "");
    }

    public static void addContentToFront(IAnkiCard card, String content, Element contentDiv) {
        contentDiv.text(content);
        card.getFront().appendChild(contentDiv);
    }

    public static void addContentToBack(IAnkiCard card, String content, Element contentDiv) {
        contentDiv.text(content);
        card.getBack().appendChild(contentDiv);
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

    /**
     * **********  Formattazione delle classi css ***********
     **/
    public static void applyLeftFormatRecursively(Element element) {
        applyLeftFormatToElement(element);
        element.children().stream().forEach(AbstractCardDecorator::applyLeftFormatToElement);    // TODO - non mi sembra ricorsivo questo. Dovrei verificare questa cosa
    }

    protected static void applyFormatRecursively(Consumer<Element> format, Element elem) {
        format.accept(elem);
        elem.children().stream().forEach(format);
    }

    protected static void applyLeftFormatToElement(Element element) {
        applyFormat(element, LEFT, LEFT, FONT_SIZE_10, AUTO);
    }

    public static void applyCenterFormatToElement(Element element) {
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

    public void setCard(IAnkiCard card) {
        this.card = card;
    }

    @Override
    public IAnkiCard create() {
        return card.create();
    }

    @Override
    public IAnkiCard create(String front, String back) {
        return card.create(front, back);
    }

    @Override
    public IAnkiCard create(Element front, Element back) {
        return card.create(front, back);
    }

    @Override
    public Element getFront() {
        return card.getFront();
    }

    @Override
    public Element getBack() {
        return card.getBack();
    }

    @Override
    public String toString() {
        return card.toString();
    }

}
