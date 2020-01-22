package main.java.webcrawlers.wordreference;

import com.google.inject.Singleton;
import main.java.webcrawlers.AbstractWebCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class WordReferenceDefinitionPage extends AbstractWebCrawler {

    public final static String WORD_REFERENCE_ESP_DEFINITION_PAGE_URL = "https://www.wordreference.com/definicion/";

    private Document definitionPage;


    public void scrapeSpanishDefinitionWord(String word) {
        this.definitionPage = scrapePage(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, word);
    }


    void setDefinitionPage(Document doc) {
        this.definitionPage = doc;
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
}
