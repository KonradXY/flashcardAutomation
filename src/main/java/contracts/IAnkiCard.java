package main.java.contracts;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public interface IAnkiCard extends Map.Entry<Element, Element>  {

	public static final String FRONT = "front";
	public static final String BACK = "back";
	public static final Tag divTag = Tag.valueOf("div");

}
