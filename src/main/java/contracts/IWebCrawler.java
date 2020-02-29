package main.java.contracts;

import java.util.List;

public interface IWebCrawler {

    List<IAnkiCard> createFlashcards(String word);

    String TRADUZIONE_CLASS = "traduzione";
    String DEFINIZIONI_CLASS = "definizioni";
    String SINONIMI_CLASS = "sinonimi";

}
