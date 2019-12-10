package main.java.contracts;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public interface IAnkiCard {

	String FRONT = "front";
	String BACK = "back";
	Tag divTag = Tag.valueOf("div");
	
	IAnkiCard create();
	IAnkiCard create(String front, String back);
	IAnkiCard create(Element front, Element back);
	
	Element getFront();
	Element getBack();
}
