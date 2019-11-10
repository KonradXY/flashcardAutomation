package main.java.baseModel;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import static main.java.contracts.IParser.NEW_LINE;
import static main.java.contracts.IParser.TAB;

import java.util.Map;

public class AbstractAnkiCard implements Map.Entry<Element, Element>  {

	public static final String FRONT = "front";
	public static final String BACK = "back";

	private final static Tag divTag = Tag.valueOf("div");

	protected Element frontHtml;
	protected Element backHtml;

	public AbstractAnkiCard() {
		// TODO - questo in teoria non ha senso. Verificare meglio !
		this.frontHtml = new Element(divTag, "");
		this.backHtml = new Element(divTag, "");

		this.frontHtml.appendChild(new Element(divTag, "").addClass(FRONT));
		this.backHtml.appendChild(new Element(divTag, "").addClass(BACK));
	}

	public AbstractAnkiCard(String front, String back) {
		// TODO - questo ha ancora meno senso 
		this.frontHtml = new Element(divTag, "");
		this.backHtml = new Element(divTag, "");

		this.frontHtml.appendChild(new Element(divTag, "").addClass(FRONT));
		this.backHtml.appendChild(new Element(divTag, "").addClass(BACK));
		
		this.frontHtml.appendText(front);
		this.backHtml.appendText(back);
	}

	@Override public Element getKey()   { return frontHtml; }
	@Override public Element getValue() { return backHtml; }
	@Override public Element setValue(Element value) { this.backHtml = value; return this.backHtml; }

	public Element setValue(String value) { this.backHtml.appendText(value); return this.backHtml; }
	
	public void setKey(Element key) { this.frontHtml = key ; }
	public void setKey(String key)  {this.frontHtml.appendText(key); }

	public Element getFrontHtml() { return frontHtml.getElementsByClass(FRONT).get(0); }
	public Element getBackHtml()  { return backHtml.getElementsByClass(BACK).get(0); }

	public void setBackHtml(Element backHtml) { this.backHtml = backHtml; }
	public void setFrontHtml(Element frontHtml) { this.frontHtml = frontHtml; }

	@Override
	public String toString() {
		return frontHtml.html().replace("\n", "") + TAB 
				+ backHtml.html().replace("\n", "") + NEW_LINE;
	}

	public boolean isCardEmpty() {
		return (frontHtml.text()+backHtml.text()).trim().equals("");
	}



}
