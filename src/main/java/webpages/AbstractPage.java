package main.java.webpages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.exceptions.WebCrawlerException;
import main.java.contracts.IWebScraper;
import main.java.enums.CssClass;


public abstract class AbstractPage implements IWebScraper {

	private static final Logger log = Logger.getLogger(AbstractPage.class);

	public static final int TIME_SLEEP 		= 1;
	public static final int MAX_NUM_EXAMPLES_PER_WORD = 2;
	public static final int NUM_TRANSLATIONS = 5;
	public static final int LOG_COUNTER 	= 5;
	public static final int TIMEOUT_SEC 	= 10;
	public static final int TIMEOUT = 1000 * TIMEOUT_SEC;

	// TODO - introdurre nuovi user agent  vedere se posso aggirare il problema del timeout durante il parsing
	public static final String MOZILLA_USER_AGENT = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";

	public static final String DISCARDED_WORD_PATH = "./src/main/resources/discardedWords.log";

	protected Document scrapePage(String urlPage, String word) {
		Document doc = null;

		try {
			doc = Jsoup.connect(createUrlAsString(urlPage, word)).userAgent(MOZILLA_USER_AGENT).timeout(TIMEOUT).get();
		} catch (MalformedURLException | HttpStatusException ex) {
			log.error("Error while scraping: " + ex);
			logDiscardedWord(word);
			throw new WebCrawlerException(ex);
		} catch (IOException ioEx) {
			log.error("Error with I/O: " + ioEx);
			throw new WebCrawlerException(ioEx);
		}

		return doc;
	}

	protected void logDiscardedWord(String word) {
		try (FileWriter fw = new FileWriter(new File(DISCARDED_WORD_PATH), true)){
			fw.write(word+"\n");
		} catch (IOException ex) {
			log.error("Errore nella scrittura sul file discarded_words_path", ex);
		} 
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
		return host + URLEncoder.encode(formatWordsForUrl(input), "UTF-8");
	}

	private String formatWordsForUrl(String words) {
		return words.trim().replace(" ", "+");
	}

	// utility methods	// TODO - questi metodi nn mi piacciono per niente qui
	public <K,V> V getFirstValueFromMap(Map<K, V> map) {
		if (map == null || map.size() == 0) 
			throw new IllegalArgumentException("Error ! Map should contain at least one value. Map: " + map);
		return map.values().iterator().next();
	}

	public <K,V> K getFirstKeyFromMap(Map<K, V> map) {
		if (map == null || map.size() == 0) 
			throw new IllegalArgumentException("Error ! Map should contain at least one value. Map: " + map);
		return map.keySet().iterator().next();
	}

	public Element getFirstTranslationFromList(Element orderedList) {
		return new Element(Tag.valueOf("p"), "").appendText(orderedList.getElementsByTag("li").get(0).text());
	}

	// TODO -- nn sono sicuro del fatto che debba aprire anche un tag div per fare sta cosa
	public Element getUnorderedListElement(CssClass css) {
		return new Element(Tag.valueOf("div"), "").appendText(css.getValue()).appendChild(new Element(Tag.valueOf("ul"), "")).addClass(css.getValue());	
	}

	public Element getListItemWithText(String text) {
		return new Element(Tag.valueOf("li"), "").appendText(text);
	}

	public Element getDivElement(CssClass css) {
		return getElementWithTag("div", css);
	}

	public Element getElementWithTag(String tag, CssClass css) {
		return new Element(Tag.valueOf(tag), "")
				.appendChild(new Element(Tag.valueOf("i"), "").appendText(css.getValue()));
	}
}
