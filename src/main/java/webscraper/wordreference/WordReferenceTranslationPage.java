package main.java.webscraper.wordreference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.webscraper.AbstractWebScraper;

@Singleton
public class WordReferenceTranslationPage extends AbstractWebScraper {

    public final static String WORD_REFERENCE_ESP_ITA_PAGE_URL = "https://www.wordreference.com/esit/";

    private Document traduzioneEspItaPage;

    void setTraduzioniEspItaPage(Document doc) {
        this.traduzioneEspItaPage = doc;
    }

    @Override
    public void scrapePageWithWord(List<IAnkiCard> cardList, String word) {
        this.traduzioneEspItaPage = scrapePage(WORD_REFERENCE_ESP_ITA_PAGE_URL, word);
        checkParsingOk(word);
    }

    private boolean checkParsingOk(String word) {
        Element noEntryFound = this.traduzioneEspItaPage.getElementById("noEntryFound");
        if (noEntryFound != null) {
            logDiscardedWord(word);
            return false;
        }

        return true;
    }

    public Map<String, String> getWordTranslation(String word) {

        Map<String, String> traduzioniMap = new LinkedHashMap<>();
        Element article = this.traduzioneEspItaPage.getElementById("article");

        Elements entries = article.getElementsByClass("superentry");
        for (Element entry : entries) {
            String parola = entry.getElementsByClass("hwblk").get(0).getElementsByTag("hw").get(0).text();
            String traduzione = entry.getElementsByTag("li").stream().map(Element::text).collect(Collectors.joining("; "));

            traduzioniMap.put(parola, traduzione);
        }

        return traduzioniMap;

    }

    public Optional<Element> getWordTips(String word) {
        Element article = this.traduzioneEspItaPage.getElementById("article");
        Elements tips = article.getElementsByClass("infoblock");

        for (Element tip : tips) {
            if (!tips.isEmpty())
                return Optional.of(tip);
        }

        return Optional.empty();
    }
}
