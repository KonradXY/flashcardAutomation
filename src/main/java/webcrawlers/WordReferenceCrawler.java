package main.java.webcrawlers;

import static main.java.utils.WebCrawlerProperties.ESP_DEF;
import static main.java.utils.WebCrawlerProperties.ESP_SINON;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Singleton
public class WordReferenceCrawler extends AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(WordReferenceCrawler.class);

    public Map<String, String> getWordDefinitions(String word) {


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

        return Collections.EMPTY_MAP;
    }

    public List<String> getWordSynonims(String word) {

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

        return Collections.EMPTY_LIST;
    }

    private URL getSynonimsUrl(String input) throws MalformedURLException {
        return getUrlFromString(ESP_SINON, input);
    }

    private URL getDefinitionUrl(String input) throws MalformedURLException {
        return getUrlFromString(ESP_DEF, input);
    }

    private String getDefinitionUrlAsString(String input) throws UnsupportedEncodingException {
        return getUrlAsString(ESP_DEF, input);
    }



}
