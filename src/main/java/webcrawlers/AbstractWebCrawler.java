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
    public static final String USER_AGENT = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";

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

    protected String getUrlAsString(String host, String input) throws UnsupportedEncodingException {
        return host + URLEncoder.encode(formatWordsForUrl(input), "UTF-8");
    }

    private String formatWordsForUrl(String words) {
        return words.trim().replace(" ", "+");
    }
}
