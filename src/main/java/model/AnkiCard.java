package main.java.model;

import static main.java.contracts.IParser.NEW_LINE;
import static main.java.contracts.IParser.TAB;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;

public class AnkiCard implements IAnkiCard {

	protected Element front;
	protected Element back;

	public AnkiCard() {
		this.front = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(FRONT_TAG));
		this.back = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(BACK_TAG));
	}

	public AnkiCard(String front, String back) {
		this();
		this.addTextContentToFront(front);
		this.addTextContentToBack(back);
	}

	public AnkiCard(Element front, Element back) {
		this();
		this.addContentToFront(front);
		this.addContentToBack(back);
	}

	public AnkiCard(Elements front, Elements back) {
		this();
		front.forEach(this::addContentToFront);
		back.forEach(this::addContentToBack);
	}

	@Override
	public IAnkiCard create() {
		return new AnkiCard();
	}

	@Override
	public IAnkiCard create(String front, String back) {
		return new AnkiCard(front, back);
	}

	@Override
	public IAnkiCard create(Element front, Element back) {
		return new AnkiCard(front, back);
	}

	@Override
	public Element getFront() {
		return front.getElementsByClass(FRONT_TAG).get(0);
	}

	@Override
	public Element getBack() {
		return back.getElementsByClass(BACK_TAG).get(0);
	}

	public void setKey(Element key) {
		this.front = key;
	}

	public void setKey(String key) {
		this.front.appendText(key);
	}

	public Element setValue(String value) {
		this.back.appendText(value);
		return this.back;
	}

	public void setBackHtml(Element backHtml) {
		this.back = backHtml;
	}

	public void setFrontHtml(Element frontHtml) {
		this.front = frontHtml;
	}

	@Override
	public void addContentToFront(String content, Element contentDiv) {
		contentDiv.text(content);
		this.getFront().appendChild(contentDiv);
	}

	public void addContentToBack(String content, Element contentDiv) {
		contentDiv.text(content);
		this.getBack().appendChild(contentDiv);
	}

	public boolean isCardEmpty() {
		return (front.text() + back.text()).trim().equals("");
	}

	public void addTextContentToFront(String content) {
		this.getFront().appendText(content);
	}

	public void addTextContentToBack(String content) {
		this.getBack().appendText(content);
	}

	@Override
	public void addContentToFront(Element elem) {
		this.getFront().appendChild(elem.clone());
	}

	@Override
	public void addContentToBack(Element elem) {
		this.getBack().appendChild(elem.clone());
	}

	@Override
	public String toString() {
		return getFront().toString().replace("\n", "") + TAB + getBack().toString().replace("\n", "") + NEW_LINE;
	}

	

}
