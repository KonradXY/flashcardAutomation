package main.java.webpages.wordreference;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import main.java.webpages.AbstractPage;

public class WordReferenceTranslationPage extends AbstractPage {

    private static final String WORD_REFERENCE_ESP_ITA_PAGE_URL = "https://www.wordreference.com/esit/";

    private Document traduzioneEspItaPage;

    // for test purposes
    void setTraduzioniEspItaPage(Document doc) {
        this.traduzioneEspItaPage = doc;
    }

    public void scrapePageWithWord(String word) {
        this.traduzioneEspItaPage = scrapePage(WORD_REFERENCE_ESP_ITA_PAGE_URL, word);
        checkTranslationIsFound(word);
    }

    private boolean checkTranslationIsFound(String word) {

        if (this.traduzioneEspItaPage == null)
            throw new IllegalStateException("the page cannot be null !");

        Element noEntryFound = this.traduzioneEspItaPage.getElementById("noEntryFound");
        if (noEntryFound != null) {
            logDiscardedWord(word);
            return false;
        }

        return true;
    }

    public Map<String, Element> getWordTranslation() {
    	
    	if (this.traduzioneEspItaPage == null)
            throw new IllegalStateException("the page cannot be null !");

        Map<String, Element> traduzioniMap = new LinkedHashMap<>();
        Element article = this.traduzioneEspItaPage.getElementById("article");

        Elements entries = article.getElementsByClass("superentry");
        for (Element entry : entries) {
            String parola = entry.getElementsByClass("hwblk").get(0).getElementsByTag("hw").get(0).text();
//            String traduzione = entry.getElementsByTag("li").stream().map(Element::text).collect(Collectors.joining("; "));
            
            Element orderedList = new Element(Tag.valueOf("div"), "").appendChild(new Element(Tag.valueOf("ol"), "").addClass("ordered_list"));
            entry.getElementsByTag("li").forEach(it -> it.appendTo(orderedList));
            
            traduzioniMap.put(parola, orderedList);
        }

        return traduzioniMap;

    }

    public Optional<Element> getWordTips() {
        Element article = this.traduzioneEspItaPage.getElementById("article");
        Elements tips = article.getElementsByClass("infoblock");

        for (Element tip : tips) {
            if (!tips.isEmpty())
                return Optional.of(tip);
        }

        return Optional.empty();
    }
}
