package main.java.abstractModel;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import static main.java.abstractModel.IParser.NEW_LINE;
import static main.java.abstractModel.IParser.TAB;

import java.util.Map;

public class AbstractAnkiCard implements Map.Entry<String, String>  {

	public static final String FRONT = "front";
	public static final String BACK = "back";

	private final static Tag divTag = Tag.valueOf("div");


	protected Element frontHtml;
	protected Element backHtml;


	protected String front;	// TODO <<-- questi sono da eliminare
	protected String back;


	public AbstractAnkiCard() {
		this.frontHtml = new Element(divTag, "");
		this.backHtml = new Element(divTag, "");

		this.frontHtml.appendChild(new Element(divTag, "").addClass(FRONT));
		this.backHtml.appendChild(new Element(divTag, "").addClass(BACK));
	}

	public AbstractAnkiCard(String front, String back) {
		new AbstractAnkiCard();
		this.front = front;
		this.back = back;
	}

	@Override public String getKey() { return front; }
	public void setKey(String key) { this.front = key ; }
	@Override public String getValue() { return back; }
	@Override public String setValue(String value) { this.back = value; return this.back; }


	public Element getFrontHtml() { return frontHtml.getElementsByClass(FRONT).get(0); }
	public Element getBackHtml() {
		return backHtml.getElementsByClass(BACK).get(0);
	}

	public void setBackHtml(Element backHtml) {
		this.backHtml = backHtml;
	}
	public void setFrontHtml(Element frontHtml) {
		this.frontHtml = frontHtml;
	}

	@Override
	public String toString() { return front + TAB + back + NEW_LINE; }

	public String toElementString() {
		return frontHtml.html().replace("\n", "") + TAB + backHtml.html().replace("\n", "") + NEW_LINE;
	}

	public boolean isCardEmpty() {
		return (frontHtml.text()+backHtml.text()).trim().equals("");
	}



}
