package main.java.model;

import main.java.contracts.IAnkiCard;

import java.util.ArrayList;
import java.util.List;

public class AnkiDeck {

    List<IAnkiCard> cards;
    String title;

    public AnkiDeck() {
        cards = new ArrayList<>();
        title = "";
    }
    
    private AnkiDeck(AnkiDeck.Builder builder) {
    	this.cards = builder.cards;
    	this.title = builder.title;
    }
    
    public void addCard(IAnkiCard card) {
    	if (this.cards == null) 
    		cards = new ArrayList<>();
    	cards.add(card);
    }

	public List<IAnkiCard> getCards() {
		return cards;
	}

	public void setDeck(List<IAnkiCard> cards) {
		this.cards = cards;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
	public static class Builder {
		private List<IAnkiCard> cards;
		private String title;
		
		public Builder withCards(List<IAnkiCard> cards) {
			this.cards = cards;
			return this;
		}
		
		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}
		
		public AnkiDeck build() {
			return new AnkiDeck(this);
		}
	}

}
