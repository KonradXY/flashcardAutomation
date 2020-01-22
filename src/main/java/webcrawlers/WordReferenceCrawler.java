package main.java.webcrawlers;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class WordReferenceCrawler extends AbstractWebCrawler {

    public final static String WORD_REFERENCE_ESP_DEFINITION_PAGE_URL = "https://www.wordreference.com/definicion/";
    public final static String WORD_REFERENCE_ESP_SINONYMS_PAGE_URL = "https://www.wordreference.com/sinonimos/";
    public final static String WORD_REFERENCE_ESP_ITA_PAGE_URL = "https://www.wordreference.com/esit/";

    private final static Logger log = Logger.getLogger(WordReferenceCrawler.class);

    private Document definitionPage;
    private Document synonimsPage;
    private Document traduzioneEspItaPage;

    // TODO - credo che dovrei creare una nuova classe per distinguere le due pagine e come tirare fuori le informazioni (qua mi sa che entra in gioco un abstract factory o qualcosa di simile)
    // TODO - da capire meglio come gestire questa circostanca (ovvero piu' pagine all'interno di un singolo sito)

    // TODO - dovrei fare dei test (almeno per i nuovi metodi). Mi ricordo che era un bel casino settare la pagina. Verificare.

    public void scrapeSpanishDefinitionWord(String word)         { this.definitionPage = scrapePage(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, word); }
    public void scrapeSpanishSynonimsPage(String word)           { this.synonimsPage = scrapePage(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, word); }
    public void scrapeSpanishItalianTranslationPage(String word) { this.traduzioneEspItaPage = scrapePage(WORD_REFERENCE_ESP_ITA_PAGE_URL, word); }

    void setTraduzioniEspItaPage(Document doc)  { this.traduzioneEspItaPage = doc; }
    void setSynonimsPage(Document doc)          { this.synonimsPage = doc; }
    void setDefinitionPage(Document doc)        { this.definitionPage = doc;}

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

    public Map<String, String> getWordDefinition(String word) {
            Element article = definitionPage.getElementById("article");        // <<--- contiene tutte le possibili definizioni di una parola.
            Elements li = article.getElementsByTag("li");
            Map<String, String> defMap = new LinkedHashMap<>();

            for (Element elem : li) {
                String s = elem.text();
                String[] split = s.split(":", 2);
                defMap.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
            }
            return defMap;
    }



    public List<String> getSynonimsFromWord(String word) {  // TODO - qua ci potrei mettere un check per verificare che la pagina scrapata sia la stessa della parola (? valutare utilita')
        Element article = synonimsPage.getElementById("article");
        Elements synonims = article.getElementsByTag("li");
        return synonims.stream().map(Element::text).collect(Collectors.toList());
    }


}
