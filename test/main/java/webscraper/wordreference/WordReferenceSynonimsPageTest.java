package main.java.webscraper.wordreference;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordReferenceSynonimsPageTest {

	
	private static final String synonimsPage = "./test/main/resources/webcrawler/wordreference/tirar - sinonimi.html";
    private static final WordReferenceSynonimsPage wordReferenceCrawler = new WordReferenceSynonimsPage();

    @BeforeAll
    public static void setup() throws Exception {
        wordReferenceCrawler.setSynonimsPage(Jsoup.parse(new File(synonimsPage), "UTF-8"));
    }
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
