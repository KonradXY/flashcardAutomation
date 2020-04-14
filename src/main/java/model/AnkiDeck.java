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

}
