package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceTranslationPageTest {

	private static final String translationPage = "./test/main/resources/webcrawler/wordreference/tirar - definizione.html";
    private static final WordReferenceTranslationPage wordReferenceCrawler = new WordReferenceTranslationPage();

    @BeforeAll
    public static void setup() throws Exception {
        wordReferenceCrawler.setTraduzioniEspItaPage(Jsoup.parse(new File(translationPage), "UTF-8"));
    }
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
