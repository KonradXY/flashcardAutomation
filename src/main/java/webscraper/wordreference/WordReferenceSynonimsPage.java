package main.java.webscraper.wordreference;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.webscraper.AbstractWebScraper;

@Singleton
public class WordReferenceSynonimsPage extends AbstractWebScraper {

    public final static String WORD_REFERENCE_ESP_SINONYMS_PAGE_URL = "https://www.wordreference.com/sinonimos/";

    private Document synonimsPage;

    @Override
    public void scrapePageWithWord(String word) {
        this.synonimsPage = scrapePage(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, word);
    }

    public List<String> getSynonimsFromWord() {

        if (this.synonimsPage == null)
            throw new IllegalStateException("the page cannot be null !");

        Element article = synonimsPage.getElementById("article");
        Elements synonims = article.getElementsByTag("li");
        return synonims.stream().map(Element::text).collect(Collectors.toList());
    }
    
    
    void setSynonimsPage(Document doc) {
    	this.synonimsPage = doc;
    }
}
