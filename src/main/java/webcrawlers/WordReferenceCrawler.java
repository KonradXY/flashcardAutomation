package main.java.webcrawlers;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.java.utils.WebCrawlerProperties.WORD_REFERENCE_ESP_DEFINITION_PAGE_URL;
import static main.java.utils.WebCrawlerProperties.WORD_REFERENCE_ESP_SINONYMS_PAGE_URL;

@Singleton
public class WordReferenceCrawler extends AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(WordReferenceCrawler.class);

    public Map<String, String> getWordDefinitionsFromWord(String word) {
            Document doc = scrapePage(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, word);
            Element article = doc.getElementById("article");        // <<--- contiene tutte le possibili definizioni di una parola.
            Elements li = article.getElementsByTag("li");
            Map<String, String> defMap = new LinkedHashMap<>();

            for (Element elem : li) {
                String s = elem.text();
                String[] split = s.split(":", 2);
                defMap.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
            }
            return defMap;
    }

    public List<String> getSynonimsFromWord(String word) {
        Document doc = scrapePage(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, word);
        Element article = doc.getElementById("article");
        Elements synonims = article.getElementsByTag("li");
        return synonims.stream().map(Element::text).collect(Collectors.toList());
    }


}
