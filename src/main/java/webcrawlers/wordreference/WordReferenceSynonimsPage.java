package main.java.webcrawlers.wordreference;

import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.webcrawlers.AbstractWebCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WordReferenceSynonimsPage extends AbstractWebCrawler {

    public final static String WORD_REFERENCE_ESP_SINONYMS_PAGE_URL = "https://www.wordreference.com/sinonimos/";

    private Document synonimsPage;

    @Override
    public void scrapePageWithWord(List<IAnkiCard> cardList, String word) {
        this.synonimsPage = scrapePage(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, word);
    }

    // TODO - qua ci potrei mettere un check per verificare che la pagina scrapata sia la stessa della parola (? valutare utilita')
    public List<String> getSynonimsFromWord(String word) {
        Element article = synonimsPage.getElementById("article");
        Elements synonims = article.getElementsByTag("li");
        return synonims.stream().map(Element::text).collect(Collectors.toList());
    }
}
