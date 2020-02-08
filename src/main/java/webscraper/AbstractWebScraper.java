package main.java.webscraper;

import main.java.contracts.IWebScraper;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public abstract class AbstractWebScraper implements IWebScraper {

    private final static Logger log = Logger.getLogger(AbstractWebScraper.class);

    public static final int TIME_SLEEP 		= 2000;
    public static final int MAX_NUM_EXAMPLES_PER_WORD = 2;
    public static final int NUM_TRANSLATIONS = 5;
    public static final int LOG_COUNTER 	= 5;
    public static final int TIMEOUT_SEC 	= 10;
    public static final int TIMEOUT = 1000 * TIMEOUT_SEC;

    public static final String USER_AGENT = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";

    public final static String discardedWordsPath 	= "./src/main/resources/discardedWords.log";

    protected Document scrapePage(String urlPage, String word) {
        Document doc = null;
        try {
            doc = Jsoup.connect(createUrlAsString(urlPage, word)).userAgent(USER_AGENT).timeout(TIMEOUT).get();
        } catch (MalformedURLException | HttpStatusException ex) {
            log.error("Error while scraping: " + ex);
            throw new RuntimeException(ex);
        } catch (IOException ioEx) {
            log.error("Error with I/O: " + ioEx);
            throw new RuntimeException(ioEx);
        }
        return doc;
    }

    protected URL createUrl(String host, String input) throws MalformedURLException {
        URL urlToScrape = null;
        try {
            String url = host + URLEncoder.encode(formatWordsForUrl(input), "UTF-8");
            urlToScrape = new URL(url);
        } catch (UnsupportedEncodingException e) {
            log.error("Errore nella creazione dell'url: " + e);
        }

        return urlToScrape;
    }

    protected String createUrlAsString(String host, String input) throws UnsupportedEncodingException {
        String url = host + URLEncoder.encode(formatWordsForUrl(input), "UTF-8");
        return url;
    }

    private String formatWordsForUrl(String words) {
        return words.trim().replace(" ", "+");
    }
}
