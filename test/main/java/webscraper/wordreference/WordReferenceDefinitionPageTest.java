package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceDefinitionPageTest {

	private static final String definitionPageHtml = "./test/main/resources/webcrawler/wordreference/tirar - definizione.html";
    private static final WordReferenceDefinitionPage wrPage = new WordReferenceDefinitionPage();

    private static Map<String, String> expectedMap;
    
    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(definitionPageHtml)))
    		throw new IllegalStateException("File must exist !");
    	
        loadExpectedMap();
    }

    
	@Test
	void checkDefinitionIsParsedCorrectlyOffline() throws IOException {
		wrPage.setDefinitionPage(Jsoup.parse(new File(definitionPageHtml), "UTF-8"));
		Map<String, String> definitionMap = wrPage.getWordDefinition();
		for (Map.Entry<String, String> entry : expectedMap.entrySet()) {
			assertTrue(definitionMap.containsKey(entry.getKey()));
			assertEquals(definitionMap.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	void checkDefinitionIsParsedCorrectlyOnline(){
    	fail("questo test non funziona correttamente. Da rivedere");
		wrPage.scrapePageWithWord("tirar");
		Map<String, String> definitionMap = wrPage.getWordDefinition();
		for (Map.Entry<String, String> entry : expectedMap.entrySet()) {
			assertTrue(definitionMap.containsKey(entry.getKey()));
			assertEquals(definitionMap.get(entry.getKey()), entry.getValue());
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
