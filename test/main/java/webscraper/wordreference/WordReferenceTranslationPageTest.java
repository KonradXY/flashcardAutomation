package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceTranslationPageTest {

	private static final String translationPage = "./test/main/resources/webcrawler/wordreference/tirar - definizione.html";
    private static final WordReferenceTranslationPage wordReferenceCrawler = new WordReferenceTranslationPage();

    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(translationPage)))
    		throw new IllegalStateException("File must exist !");
    	
        wordReferenceCrawler.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
    }
	
	@Test
	void checkWordTranslationsAreParsedCorrectly() {
		Map<String, String> map = wordReferenceCrawler.getWordTranslation();
		System.out.println(map);
	}
	
	
	@Test
	void checkWordTipsAreParsedCorrectly() {
		Optional<Element> tip = wordReferenceCrawler.getWordTips();
		System.out.println(tip);
	}

}
