package main.java.model;

import main.java.contracts.IAnkiCard;

import java.util.ArrayList;
import java.util.List;

public class AnkiDeck {

    List<IAnkiCard> deck;
    String title;

    public AnkiDeck() {
        deck = new ArrayList<>();
        title = "";
    }
    
    public void addCard(IAnkiCard card) {
    	if (this.deck == null) 
    		deck = new ArrayList<>();
    	deck.add(card);
    }

	public List<IAnkiCard> getDeck() {
		return deck;
	}

	public void setDeck(List<IAnkiCard> deck) {
		this.deck = deck;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    

}
