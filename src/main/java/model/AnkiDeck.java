package main.java.model;

import main.java.contracts.IAnkiCard;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AnkiDeck {

	private List<IAnkiCard> cards;
    private String title;
    private String destFolder;

    private AnkiDeck() {
        cards = new ArrayList<>();
    }
    
    private AnkiDeck(AnkiDeck.Builder builder) {
    	this.cards = builder.cards;
    	this.title = builder.title;
    	this.destFolder = builder.destFolder;
    }
    
    public void addCard(IAnkiCard card) {
    	if (this.cards == null) 
    		cards = new ArrayList<>();
    	cards.add(card);
    }

    
    public String getDest() {
    	return this.destFolder + this.title;
    }
    
    public Path getPathDest() {
    	return Paths.get(this.destFolder +  this.title);
    }
    
    
    // GETTERs and SETTERs
	public List<IAnkiCard> getCards() {
		if (cards == null)
			cards = new ArrayList<>();
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
	
	public String getDestFolder() {
		return destFolder;
	}

	public void setDestFolder(String destFolder) {
		this.destFolder = destFolder;
	}


	public static class Builder {
		private List<IAnkiCard> cards;
		private String title;
		private String destFolder;
		
		public Builder withCards(List<IAnkiCard> cards) {
			this.cards = cards;
			return this;
		}
		
		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Builder withDestFolder(String destFolder) {
			this.destFolder = destFolder;
			return this;
		}
		
		public AnkiDeck build() {
			return new AnkiDeck(this);
		}
	}

	
	

	@Override
	public String toString() {
		return "AnkiDeck [title=" + title + ", destFolder=" + destFolder + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destFolder == null) ? 0 : destFolder.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnkiDeck other = (AnkiDeck) obj;
		if (destFolder == null) {
			if (other.destFolder != null)
				return false;
		} else if (!destFolder.equals(other.destFolder))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
}
