package main.java.webcrawlers;

import main.java.contracts.IAnkiCard;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReversoSpanishCrawlerTest {


    private ReversoSpanishCrawler reversoSpanishCrawler = new ReversoSpanishCrawler();
    private static final String SPANISH_WORD = "ejemplo";


    @Test
    void testProva() {
        // TODO - disaccoppiare get della pagina da estrazione del contenuto
        List<IAnkiCard> cardList = reversoSpanishCrawler.getReversoExamplesFromWord(SPANISH_WORD);
        for (IAnkiCard card : cardList) {
            System.out.println(card);
        }


    }

}