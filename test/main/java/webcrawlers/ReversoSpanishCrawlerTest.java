package main.java.webcrawlers;

import main.java.contracts.IAnkiCard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class ReversoSpanishCrawlerTest {

    private static final String SPANISH_TEST_WORD = "ejemplo";
    private static final String htmlTestFile = "./test/main/resources/webcrawler/reverso_test_example.html";


    private final ReversoSpanishCrawler reversoSpanishCrawler = new ReversoSpanishCrawler();

    @Test
    void cardsAreCreatedCorrectly() throws IOException {
        Document scrapedPage = Jsoup.parse(new File(htmlTestFile), "UTF-8");
        List<IAnkiCard> cardList = reversoSpanishCrawler.createAnkiCardsFromContent(scrapedPage, SPANISH_TEST_WORD);
        fail("Not implemented yet");
    }

    // TODO - dovrei fare un builder delle carte in modo d poterle create e testare tranquillamente

    /*
    Output delle carte:
    <div class="front"> <b class="wordLearned">ejemplo</b> <p class="traduzione">È pressoché impossibile trovare una famiglia per bambini disabili, per esempio.</p> <p class="contenuto">Es casi imposible ubicar bebés discapacitados, por ejemplo.</p></div>	<div class="back"></div>
    <div class="front"> <b class="wordLearned">ejemplo</b> <p class="traduzione">Per esempio, non avevi bisogno di togliere la focaccia.</p> <p class="contenuto">Por ejemplo, no necesitabas quitar la focaccia.</p></div>	<div class="back"></div>
     */


}