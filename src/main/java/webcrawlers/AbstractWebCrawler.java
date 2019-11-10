package main.java.webcrawlers;

import static main.java.utils.WebCrawlerProperties.TIMEOUT_SEC;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

public class AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(AbstractWebCrawler.class);
    public static final int TIMEOUT = 1000 * TIMEOUT_SEC;

    protected Document doc;

    protected URL getUrlFromString(String host, String input) throws MalformedURLException {
        URL urlToScrape = null;
        try {
            String url = host + URLEncoder.encode(formatWordsForUrl(input), "UTF-8");
            urlToScrape = new URL(url);
        } catch (UnsupportedEncodingException e) {
            log.error("Errore nella creazione dell'url: " + e);
        }

        return urlToScrape;
    }

    private String formatWordsForUrl(String words) {
        return words.trim().replace(" ", "+");
    }
}
