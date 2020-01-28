package main.java.contracts;

import java.util.List;

public interface IWebCrawler {

    List<IAnkiCard> createFlashcards(String word);

}
