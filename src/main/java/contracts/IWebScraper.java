package main.java.contracts;

import java.util.List;

public interface IWebScraper {

    public void scrapePageWithWord(List<IAnkiCard> cardList, String word);
}