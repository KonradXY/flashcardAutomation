package main.java.webcrawlers;

import static org.junit.jupiter.api.Assertions.*;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static main.java.utils.WebCrawlerProperties.REVERSO_ESP_ITA_TRANSLATION_PAGE_URL;

class ReversoSpanishCrawlerTest {

    private static final String SPANISH_TEST_WORD = "ejemplo";
    private static final String htmlTestFile = "./test/main/resources/webcrawler/reverso_test_example.html";
    private static String htmlTestContent = "";

    private final ReversoSpanishCrawler reversoSpanishCrawler = new ReversoSpanishCrawler();

    @BeforeEach
    public void setup() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(htmlTestFile), "UTF-8"))) {
            StringBuilder sb = new StringBuilder();
            br.lines().forEach(sb::append);
            htmlTestContent = sb.toString();
        }

        // TODO - famo lo scraping della stessa pagina offlline ? - da verificare !

    }


    @Test
    void testProva() {
        List<IAnkiCard> cardList = reversoSpanishCrawler.getReversoExamplesFromWord(SPANISH_TEST_WORD);
        for (IAnkiCard card : cardList) {
            System.out.println(card);
        }

        Document scrapedPage = reversoSpanishCrawler.scrapePage(REVERSO_ESP_ITA_TRANSLATION_PAGE_URL, SPANISH_TEST_WORD);

        assertEquals(htmlTestContent.replaceAll("\\s+",""),
                scrapedPage.toString().replaceAll("\\s+",""));


    }



}