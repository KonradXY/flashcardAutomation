package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.contracts.IAnkiCard;

public class AnkiDeck implements IAnkiCard {
	
	private String name;
	private List<IAnkiCard> deck;

	
	public void addCardToDeck(IAnkiCard card) {
		getDeck().add(card);
	}
	
	public List<IAnkiCard> getDeck() {
		if (deck == null) deck = new ArrayList<>();
		return deck;
	}
}
