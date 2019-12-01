package main.java.contracts;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public interface IAnkiCard {

	public static final String FRONT = "front";
	public static final String BACK = "back";
	public static final Tag divTag = Tag.valueOf("div");
	
	public IAnkiCard create();
	public IAnkiCard create(String front, String back);
	public IAnkiCard create(Element front, Element back);
	
	public Element getFront();
	public Element getBack();
}
