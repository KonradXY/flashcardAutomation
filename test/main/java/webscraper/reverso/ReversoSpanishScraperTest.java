package main.java.webscraper.reverso;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReversoSpanishScraperTest {

    private static final String definitionPageHtml = "./test/main/resources/webcrawler/reverso/tirar - definizione.html";
    private static final ReversoDefinitionPage reversoDefinitionPage = new ReversoDefinitionPage();

    private static Map<String, String> expectedMap;

    @BeforeAll
    public static void setup() throws Exception {
        if (!Files.exists(Paths.get(definitionPageHtml)))
            throw new IllegalStateException("File must exist !");

        loadExpectedMap();
    }

    @Test
    void checkParseIsDoneCorrectlyOffline() throws IOException {
        reversoDefinitionPage.setDefinitionPage(Jsoup.parse(new File(definitionPageHtml), "UTF-8"));
        Map<String, String> map = reversoDefinitionPage.getTraduzioni();
        assertEquals(expectedMap, map);
    }

    @Test
    void checkParseIsDoneCorrectlyOnline() {
        reversoDefinitionPage.scrapePageWithWord("tirar");
        Map<String, String> map = reversoDefinitionPage.getTraduzioni();
        assertEquals(expectedMap, map);
    }

    private static void loadExpectedMap() {
        expectedMap = new HashMap<>();
        expectedMap.put("Avete provato a buttare delle rocce nello sciacquone.","Intentaste tirar piedras por el retrete.");
        expectedMap.put("L'ho trovato, quando Frank mi ha chiesto di buttare le sue cose.","Lo encontré cuando Frank me pidió tirar sus cosas.");
    }

}