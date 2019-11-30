package main.java.model;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import static main.java.contracts.IParser.NEW_LINE;
import static main.java.contracts.IParser.TAB;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class AbstractAnkiCard implements Map.Entry<Element, Element>  {

	protected static final String FRONT = "front";
	protected static final String BACK = "back";

	protected final static Tag divTag = Tag.valueOf("div");

	protected Element front;
	protected Element back;

	public AbstractAnkiCard() {
		this.front = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(FRONT));
		this.back = new Element(divTag, "").appendChild(new Element(divTag, "").addClass(BACK));
	}

	public AbstractAnkiCard(String front, String back) {
		this();
		this.addTextContentToFront(front);
		this.addTextContentToBack(back);
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
