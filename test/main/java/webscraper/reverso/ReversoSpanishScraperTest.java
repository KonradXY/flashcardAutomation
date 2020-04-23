package main.java.webscraper.reverso;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

class ReversoSpanishScraperTest {

    private static final String definitionPageHtml = "./test/main/resources/webcrawler/reverso/tirar - definizione.html";
    private static final ReversoDefinitionPage reversoCrawler = new ReversoDefinitionPage();

    private static Map<String, String> expectedMap;

    @BeforeAll
    public static void setup() throws Exception {
        if (!Files.exists(Paths.get(definitionPageHtml)))
            throw new IllegalStateException("File must exist !");

        reversoCrawler.setDefinitionPage(Jsoup.parse(new File(definitionPageHtml), "UTF-8"));

    }


    @Test
    void checkParseIsDoneCorrectly() {
        reversoCrawler.createAnkiCardsFromContent("tirar");
    }


}