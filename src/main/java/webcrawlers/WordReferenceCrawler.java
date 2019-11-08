package main.java.webcrawlers;

import static main.java.utils.WebCrawlerProperties.*;

import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordReferenceCrawler extends AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(WordReferenceCrawler.class);

    public Map<String, String> getWordDefinitions(String word) {


        try {
            doc = Jsoup.parse(getDefinitionUrl(word), TIMEOUT);

            Element article = doc.getElementById("article");        // <<--- contiene tutte le possibili definizioni di una parola.
            Elements li = article.getElementsByTag("li");
            Map<String, String> defMap = new LinkedHashMap<>();

            for (Element elem : li) {
                String s = elem.text();
                String[] split = s.split(":", 2);
                defMap.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
            }
            return defMap;


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



}
