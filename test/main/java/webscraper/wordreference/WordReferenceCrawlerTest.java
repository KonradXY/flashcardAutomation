package main.java.webscraper.wordreference;

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
	private static final WordReferenceTranslationPage wordReferenceCrawler = new WordReferenceTranslationPage();

	private static Map<String, String> expectedMap;

	@BeforeAll
	public static void setup() throws Exception {
		wordReferenceCrawler.setTraduzioniEspItaPage(Jsoup.parse(new File(htmlTestFile), "UTF-8"));
		loadExpectedMap();
	}

	@Test
	void getWordTranslationsWorksCorrecty() {
		Map<String, String> traduzioni  = wordReferenceCrawler.getWordTranslation();



		assertEquals(2, traduzioni.size());
		assertEquals(expectedMap, traduzioni);
	}

	@Test
	void getTipsWorksCorrectly() {
		Optional<Element> tip = wordReferenceCrawler.getWordTips();
		final String tip_test = "<span class=\"infoblock\"><b>aceite</b> no se traduce nunca por la palabra italiana <i>aceto</i>.</span>";

		assertTrue(tip.isPresent());
		assertEquals(tip_test, tip.get().toString());
	}


	private static void loadExpectedMap() {
		expectedMap = new HashMap<>();
		expectedMap.put("aceite", "olio");
		expectedMap.put("aceitar", "(Tec) oliare; (Culin) condire con olio");
	}
}