package main.java.webcrawlers;

import static main.java.utils.WebCrawlerProperties.WORD_REFERENCE_ESP_DEFINITION_PAGE_URL;
import static main.java.utils.WebCrawlerProperties.WORD_REFERENCE_ESP_SINONYMS_PAGE_URL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Singleton;

@Singleton
public class WordReferenceCrawler extends AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(WordReferenceCrawler.class);

    public Map<String, String> getWordDefinitionsFromWord(String word) {


        try {
            doc = Jsoup.connect(getDefinitionUrlAsString(word)).userAgent(USER_AGENT).timeout(TIMEOUT).get();

            Element article = doc.getElementById("article");        // <<--- contiene tutte le possibili definizioni di una parola.
            Elements li = article.getElementsByTag("li");
            Map<String, String> defMap = new LinkedHashMap<>();

            for (Element elem : li) {
                String s = elem.text();
                String[] split = s.split(":", 2);
                defMap.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
            }
            return defMap;

        } catch (NullPointerException npex) {
            log.error("NullPointer: " + npex);
        } catch (MalformedURLException | HttpStatusException ex) {
            log.error("Error while scraping: " + ex);
        } catch (IOException ioEx) {
            log.error("Error with I/O: " + ioEx);
        }

        return Collections.emptyMap();
    }

    public List<String> getSynonimsFromWord(String word) {

        try {
            Document doc = Jsoup.parse(getSynonimsUrl(word), TIMEOUT);
            Element article = doc.getElementById("article");
            Elements synonims = article.getElementsByTag("li");
            return synonims.stream().map(Element::text).collect(Collectors.toList());
        } catch (NullPointerException npex) {
            log.error("Error while scraping: " + npex);
        } catch (MalformedURLException | HttpStatusException ex) {
            log.error("Error while scraping: " + ex);
        } catch (IOException ioEx) {
            log.error("Error with I/O: " + ioEx);
        }

        return Collections.emptyList();
    }

    private URL getSynonimsUrl(String input) throws MalformedURLException {
        return getUrlFromString(WORD_REFERENCE_ESP_SINONYMS_PAGE_URL, input);
    }

    private URL getDefinitionUrl(String input) throws MalformedURLException {
        return getUrlFromString(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, input);
    }

    private String getDefinitionUrlAsString(String input) throws UnsupportedEncodingException {
        return getUrlAsString(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, input);
    }



}
