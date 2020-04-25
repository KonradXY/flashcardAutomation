package main.java.webpages.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.webpages.AbstractPageTest;
import main.java.webpages.wordreference.WordReferenceTranslationPage;

class WordReferenceTranslationPageTest extends AbstractPageTest {

	private static final String translationPage = "./test/main/resources/webcrawler/wordreference/tirar - traduzione.html";
    private static final WordReferenceTranslationPage wrPage = new WordReferenceTranslationPage();

    private static Map<String, Element> expectedTranslationMap;

    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(translationPage)))
    		throw new IllegalStateException("File must exist !");

    	loadExpectedTranslationMap();


    }

	@Test
	void checkWordTranslationsAreParsedCorrectlyOffline() throws IOException {
		wrPage.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
		Map<String, Element> map = wrPage.getWordTranslation();
		
		assertEquals(expectedTranslationMap.keySet(), map.keySet());
		assertTrue(elementsAreEquals(getFirstObjectFromMap(expectedTranslationMap), getFirstObjectFromMap(map)));
	}

	@Test
	void checkWordTranslationsAreParsedCorrectlyOnline() throws IOException {
		wrPage.scrapePageWithWord("tirar");
		Map<String, Element> map = wrPage.getWordTranslation();
		
		assertEquals(expectedTranslationMap.keySet(), map.keySet());
		assertTrue(elementsAreEquals(getFirstObjectFromMap(expectedTranslationMap), getFirstObjectFromMap(map)));
	}


	@Test
	void checkWordTipsAreParsedCorrectly() throws IOException {
    	fail("questo test nn funziona perche' nn e' presente nessun tip");
		wrPage.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
		Optional<Element> tip = wrPage.getWordTips();
		System.out.println(tip);
	}

	private static void loadExpectedTranslationMap() {
		List<String> words = Arrays.asList("tirare, gettare", 
										"(derribar) buttare giù",
										"(desechar, dinero) buttare (via)",
										"(foto) scattare",
										"(disparar) sparare",
										"tirare",
										"(fig) attrarre",
										"(fam: andar) andare",
										"(tender) tendere",
										"(abalanzarse) lanciarsi",
										"(tumbarse) gettarsi");
		
		Element orderedList = new Element(Tag.valueOf("div"), "").appendChild(new Element(Tag.valueOf("ol"),""));
		words.forEach(it -> new Element(Tag.valueOf("li"), "").appendText(it).appendTo(orderedList.child(0)));
		
		expectedTranslationMap = new HashMap<>();
		expectedTranslationMap.put("tirar", orderedList);
	}
	
	

}
