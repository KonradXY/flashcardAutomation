package main.java.contracts;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public interface IAnkiCard {

	String FRONT_TAG = "front";
	String BACK_TAG = "back";
	Tag divTag = Tag.valueOf("div");
	
	IAnkiCard create();
	IAnkiCard create(String front, String back);
	IAnkiCard create(Element front, Element back);
	
	Element getFront();
	Element getBack();

	String toString();
	
	void addContentToFront(String word, Element addClass);
	void addContentToBack(String word, Element addClass);

}
