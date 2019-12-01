package main.java.model;

import static main.java.contracts.IParser.NEW_LINE;
import static main.java.contracts.IParser.TAB;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;

public class AnkiCard implements IAnkiCard  {

	protected Element front;
	protected Element back;

	public AnkiCard() {
		this.front = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(FRONT));
		this.back = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(BACK));
	}

	public AnkiCard(String front, String back) {
		this();
		this.addTextContentToFront(front);
		this.addTextContentToBack(back);
	}
	
	public AnkiCard(Element front, Element back) {
		this();
		this.addElementToFront(front);
		this.addElementToBack(back);
	}
	
	public AnkiCard(Elements front, Elements back) {
		this();
		front.forEach(it -> this.addElementToFront(it));
		back.forEach(it -> this.addElementToBack(it));
	}


	@Override public Element getKey()   { return getFront(); }
	@Override public Element getValue() { return getBack(); }
	@Override public Element setValue(Element value) { this.back = value; return this.back; }

	public Element getFront() { return front.getElementsByClass(FRONT).get(0); }
	public Element getBack()  { return back.getElementsByClass(BACK).get(0); }

	public void setKey(Element key) 		{ this.front = key ; }
	public void setKey(String key)  		{ this.front.appendText(key); }
	public Element setValue(String value) 	{ this.back.appendText(value); return this.back; }

	public void setBackHtml(Element backHtml) 	{ this.back = backHtml; }
	public void setFrontHtml(Element frontHtml) { this.front = frontHtml; }


	public boolean isCardEmpty() {
		return (front.text()+back.text()).trim().equals("");
	}

	public void addTextContentToFront(String content) {
		this.getFront().appendText(content);
	}

	public void addTextContentToBack(String content) {
		this.getBack().appendText(content);
	}

	public void addElementToFront(Element elem) {
		this.getFront().appendChild(elem.clone());
	}

	public void addElementToBack(Element elem) {
		this.getBack().appendChild(elem.clone());
	}

	@Override
	public String toString() {
		return getFront().toString().replace("\n", "") + TAB 
				+ getBack().toString().replace("\n", "") + NEW_LINE;
	}

}
