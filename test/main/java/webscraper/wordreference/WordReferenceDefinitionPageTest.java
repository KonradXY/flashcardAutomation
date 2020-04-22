package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceDefinitionPageTest {

	private static final String definitionPageHtml = "./test/main/resources/webcrawler/wordreference/tirar - definizione.html";
    private static final WordReferenceDefinitionPage wordReferenceCrawler = new WordReferenceDefinitionPage();

    private static Map<String, String> expectedMap;
    
    @BeforeAll
    public static void setup() throws Exception {
        wordReferenceCrawler.setDefinitionPage(Jsoup.parse(new File(definitionPageHtml), "UTF-8"));
        loadExpectedMap();
    }

    
	@Test
	void checkDefinitionAreCrawledCorrectly() {
		Map<String, String> definitionMap = wordReferenceCrawler.getWordDefinition();
		for (Map.Entry<String, String> entry : definitionMap.entrySet()) {
			assertTrue(expectedMap.containsKey(entry.getKey()));
			assertEquals(expectedMap.get(entry.getKey()), entry.getValue());
		}
	}
	
	
	private static void loadExpectedMap() {
		expectedMap = new HashMap<>();
		expectedMap.put("adv. l. con que se denota la situación de personas o cosas que circundan a otras", "la piscina tiene árboles alrededor.");
		expectedMap.put("m. Contorno de un lugar. Más en pl.", "se ha mudado a los alrededores del pueblo.");
		expectedMap.put("alrededor de loc. adv. Precediendo a una expresión numérica, aproximadamente, poco más o menos", "alrededor de diez mil espectadores.");
		expectedMap.put("loc. prepos. Rodeando, en torno a", "viaje alrededor del mundo; llegaremos alrededor del día diez.");
	}
}
