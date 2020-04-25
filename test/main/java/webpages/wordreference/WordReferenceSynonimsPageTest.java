package main.java.webpages.wordreference;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.webpages.wordreference.WordReferenceSynonimsPage;

class WordReferenceSynonimsPageTest {

	
	private static final String synonimsPage = "./test/main/resources/webcrawler/wordreference/tirar - sinonimi.html";
    private static final WordReferenceSynonimsPage wrPage = new WordReferenceSynonimsPage();
    
    private static List<String> expectedList;

    @BeforeAll
    public static void setup() throws Exception {
    	if (!Files.exists(Paths.get(synonimsPage)))
    		throw new IllegalStateException("File must exist !");
    	

        loadExpectedList();
    }
	
	@Test
	void parseIsDoneCorrecltyOffline() throws IOException {
		wrPage.setSynonimsPage(Jsoup.parse(new File(synonimsPage), "UTF-8"));
		List<String> synonimsList = wrPage.getSynonimsFromWord();
		
		assertEquals(10, synonimsList.size());
		assertEquals(expectedList, synonimsList);
	}

	@Test
	void parseIsDoneCorrecltyOnline() {
		wrPage.scrapePageWithWord("tirar");
		List<String> synonimsList = wrPage.getSynonimsFromWord();

		assertEquals(10, synonimsList.size());
		assertEquals(expectedList, synonimsList);
	}

	
	private static void loadExpectedList() {
		expectedList = new ArrayList<>();
		expectedList.add("arrojar, lanzar, echar, precipitar, derramar, verter, descargar");
		expectedList.add("Antónimos: coger, recoger");
		expectedList.add("derribar, derrumbar, tumbar, demoler, abatir, destruir");
		expectedList.add("disparar, descargar, cañonear, ametrallar");
		expectedList.add("malgastar, dilapidar, desperdiciar, desaprovechar");
		expectedList.add("Antónimos: ahorrar, aprovechar");
		expectedList.add("arrastrar, atraer, impulsar");
		expectedList.add("imprimir, editar, publicar, lanzar");
		expectedList.add("tenderse, tumbarse, acostarse, echarse, yacer");
		expectedList.add("Antónimos: levantarse");
	}
}
