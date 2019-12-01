package main.java.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import main.java.contracts.IAnkiCard;

public class AnkiDeck implements IAnkiCard {
	
	private String name;
	private List<IAnkiCard> deck;

	@Override public Element getKey() {		return null; }
	@Override public Element getValue() {		return null; }
	@Override public Element setValue(Element value) { return null; }
	
	
	public void addCardToDeck(IAnkiCard card) {
		getDeck().add(card);
	}
	
	public List<IAnkiCard> getDeck() {
		if (deck == null) deck = new ArrayList<>();
		return deck;
	}
}
