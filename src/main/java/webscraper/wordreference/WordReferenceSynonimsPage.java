package main.java.webscraper.wordreference;

import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.webscraper.AbstractWebScraper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WordReferenceSynonimsPage extends AbstractWebScraper {

    public final static String WORD_REFERENCE_ESP_SINONYMS_PAGE_URL = "https://www.wordreference.com/sinonimos/";

    private Document synonimsPage;

    @Override
    public void scrapePageWithWord(List<IAnkiCard> cardList, String word) {
        this.synonimsPage = scrapePage(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, word);
    }

    public List<String> getSynonimsFromWord(String word) {
        Element article = synonimsPage.getElementById("article");
        Elements synonims = article.getElementsByTag("li");
        return synonims.stream().map(Element::text).collect(Collectors.toList());
    }
}