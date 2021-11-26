package main.java.model.deck;

import main.java.model.card.IAnkiCard;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AnkiDeck {

    private static final String TXT_EXTENSION = ".txt";

    private List<IAnkiCard> cards;
    private String title;
    private String destFolder;

    private AnkiDeck() {
        cards = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private AnkiDeck(AnkiDeck.Builder builder) {
        this.cards = (List<IAnkiCard>) builder.cards;
        this.title = builder.title;
        this.destFolder = builder.destFolder;
    }

    public void addCard(IAnkiCard card) {
        cards.add(card);
    }

    public void addCards(List<IAnkiCard> cards) {
        this.cards.addAll(cards);
    }


    public String getFileDestination() {
        return this.destFolder + this.title + TXT_EXTENSION;
    }

    public Path getPathDest() {
        return Paths.get(this.destFolder + this.title + TXT_EXTENSION);
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

    public String getDestFolder() {
        return destFolder;
    }

    public void setDestFolder(String destFolder) {
        this.destFolder = destFolder;
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

    public static class Builder {
        private List<? extends IAnkiCard> cards;
        private String title;
        private String destFolder;

        public Builder withCards(List<? extends IAnkiCard> cards) {
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
            if (this.cards == null) this.cards = new ArrayList<>();
            return new AnkiDeck(this);
        }
    }


}
