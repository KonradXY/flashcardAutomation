package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
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
    private static final WordReferenceTranslationPage wordReferenceCrawler = new WordReferenceTranslationPage();

    private static Map<String, String> expectedTranslationMap;

    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(translationPage)))
    		throw new IllegalStateException("File must exist !");

    	loadExpectedTranslationMap();

        wordReferenceCrawler.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
    }

	@Test
	void checkWordTranslationsAreParsedCorrectly() {
		Map<String, String> map = wordReferenceCrawler.getWordTranslation();
		assertEquals(expectedTranslationMap, map);
	}


	@Test
	void checkWordTipsAreParsedCorrectly() {
		Optional<Element> tip = wordReferenceCrawler.getWordTips();
		System.out.println(tip);
	}

	private static void loadExpectedTranslationMap() {
		expectedTranslationMap = new HashMap<>();
		expectedTranslationMap.put("tirar","tirare, gettare; \\n(derribar) buttare giù; \\n(desechar, dinero) buttare (via); \\n(foto) scattare; \\n(disparar) sparare; \\ntirare; \\n(fig) attrarre; \\n(fam: andar) andare; \\n(tender) tendere; \\n(abalanzarse) lanciarsi; \\n(tumbarse) gettarsi");
	}

}
