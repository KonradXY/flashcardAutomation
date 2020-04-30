package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceTranslationPageTest {

	private static final String translationPage = "./test/main/resources/webcrawler/wordreference/tirar - traduzione.html";
    private static final WordReferenceTranslationPage wrPage = new WordReferenceTranslationPage();

    private static Map<String, String> expectedTranslationMap;

    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(translationPage)))
    		throw new IllegalStateException("File must exist !");

    	loadExpectedTranslationMap();
    }

	@Test
	void checkWordTranslationsAreParsedCorrectlyOffline() throws IOException {
		wrPage.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
		Map<String, String> map = wrPage.getWordTranslation();
		assertEquals(expectedTranslationMap, map);
	}

	@Test
	void checkWordTranslationsAreParsedCorrectlyOnline() throws IOException {
		wrPage.scrapePageWithWord("tirar");
		Map<String, String> map = wrPage.getWordTranslation();
		assertEquals(expectedTranslationMap, map);
	}


	@Test
	void checkWordTipsAreParsedCorrectly() throws IOException {
    	fail("questo test nn funziona perche' nn e' presente nessun tip");
		wrPage.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
		Optional<Element> tip = wrPage.getWordTips();
		System.out.println(tip);
	}

	private static void loadExpectedTranslationMap() {
		expectedTranslationMap = new HashMap<>();
		expectedTranslationMap.put("tirar","tirare, gettare; \\n(derribar) buttare giù; \\n(desechar, dinero) buttare (via); \\n(foto) scattare; \\n(disparar) sparare; \\ntirare; \\n(fig) attrarre; \\n(fam: andar) andare; \\n(tender) tendere; \\n(abalanzarse) lanciarsi; \\n(tumbarse) gettarsi");
	}

}
