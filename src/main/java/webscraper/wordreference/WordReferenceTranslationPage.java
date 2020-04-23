package main.java.webscraper.wordreference;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Singleton;

import main.java.webscraper.AbstractWebScraper;

@Singleton
public class WordReferenceTranslationPage extends AbstractWebScraper {

    public final static String WORD_REFERENCE_ESP_ITA_PAGE_URL = "https://www.wordreference.com/esit/";

    private Document traduzioneEspItaPage;

    // for test purposes
    void setTraduzioniEspItaPage(Document doc) {
        this.traduzioneEspItaPage = doc;
    }

    @Override
    public void scrapePageWithWord(String word) {
        this.traduzioneEspItaPage = scrapePage(WORD_REFERENCE_ESP_ITA_PAGE_URL, word);
        checkTranslationIsFound(word);
    }

    private boolean checkTranslationIsFound(String word) {
        Element noEntryFound = this.traduzioneEspItaPage.getElementById("noEntryFound");
        if (noEntryFound != null) {
            logDiscardedWord(word);
            return false;
        }

        return true;
    }

    public Map<String, String> getWordTranslation() {

        Map<String, String> traduzioniMap = new LinkedHashMap<>();
        Element article = this.traduzioneEspItaPage.getElementById("article");

        Elements entries = article.getElementsByClass("entry").get(0).getElementsByTag("li");
        for (Element entry : entries) {
            String traduzione = entry.getElementsByTag("span").text();
            String significato = entry.text().replace(traduzione, "");
            traduzioniMap.put(significato, traduzione);
        }

        return traduzioniMap;

    }

}
