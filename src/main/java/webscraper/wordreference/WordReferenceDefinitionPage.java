package main.java.webscraper.wordreference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.webscraper.AbstractWebScraper;

@Singleton
public class WordReferenceDefinitionPage extends AbstractWebScraper {

    public final static String WORD_REFERENCE_ESP_DEFINITION_PAGE_URL = "https://www.wordreference.com/definicion/";

    private Document definitionPage;

    @Override
    public void scrapePageWithWord(String word) {
        this.definitionPage = scrapePage(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, word);
    }

    public Map<String, String> getWordDefinition() {
        Element article = definitionPage.getElementById("article");        
        Elements li = article.getElementsByTag("li");
        Map<String, String> defMap = new LinkedHashMap<>();

        for (Element elem : li) {
            String s = elem.text();
            String[] split = s.split(":", 2);
            defMap.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
        }
        
        return defMap;
    }
    
    
    void setDefinitionPage(Document doc) {
    	this.definitionPage = doc;
    }
}
