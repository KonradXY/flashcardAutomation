package main.java.webcrawlers.wordreference;

import main.java.webcrawlers.wordreference.WordReferenceTranslationPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WordReferenceCrawlerTest {

    private static final String htmlTestFile = "./test/main/resources/webcrawler/word_reference_single_definition_test_example.html";
    private static final String SPANISH_TEST_WORD = "aceite";

    private static final WordReferenceTranslationPage wordReferenceCrawler = new WordReferenceTranslationPage();

    @BeforeAll
    public static void setup() throws Exception {
        wordReferenceCrawler.setTraduzioniEspItaPage(Jsoup.parse(new File(htmlTestFile), "UTF-8"));
    }

    @Test
    void getWordTranslationsWorksCorrecty() {
        Map<String, String> traduzioni  = wordReferenceCrawler.getWordTranslation(SPANISH_TEST_WORD);

        Map<String, String> test_map = new HashMap<>();
        test_map.put("aceite", "olio");
        test_map.put("aceitar", "(Tec) oliare; (Culin) condire con olio");

        assertEquals(2, traduzioni.size());
        assertEquals(test_map, traduzioni);

    }

    @Test
    void getTipsWorksCorrectly() {
        Optional<Element> tip = wordReferenceCrawler.getWordTips(SPANISH_TEST_WORD);
        final String tip_test = "<span class=\"infoblock\"><b>aceite</b> no se traduce nunca por la palabra italiana <i>aceto</i>.</span>";

        assertTrue(tip.isPresent());
        assertEquals(tip_test, tip.get().toString());
    }
}