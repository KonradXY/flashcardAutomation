package main.java.webcrawlers;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AbstractCrawler {

    private final static Logger log = Logger.getLogger(AbstractCrawler.class);

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
