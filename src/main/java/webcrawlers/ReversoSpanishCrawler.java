package main.java.webcrawlers;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.baseModel.SimpleAnkiCard;
import main.java.modelDecorator.CardDecorator;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static main.java.utils.WebCrawlerProperties.ESP_ITA_URL;
import static main.java.utils.WebCrawlerProperties.NUM_EXAMPLES;
import static main.java.utils.WebCrawlerProperties.NUM_TRANSLATIONS;
import static main.java.utils.WebCrawlerProperties.TIMEOUT_SEC;

public class ReversoSpanishCrawler extends AbstractCrawler {

	private static Document doc;
	private final static Logger log = Logger.getLogger(ReversoSpanishCrawler.class);

	private static final int TIMEOUT = 1000 * TIMEOUT_SEC;

	private static final String EXAMPLES_ID = "examples-content";
	private static final String TRANSLATION_CONTENT_ID = "translations-content";
	private static final String EXAMPLE_CLASS = "example";
	private static final String CONTENT_CLASS = "src ltr";
	private static final String TRADUCT_CLASS = "trg ltr";

	private static final String ANCHOR_TAG = "a";

	public List<AbstractAnkiCard> getExamplesFromWord(String word)  {
		List<AbstractAnkiCard> cardList = new ArrayList<>();

		try {
			URL urlToScrape = getUrlFromString(ESP_ITA_URL,word);
			doc = Jsoup.parse(urlToScrape, TIMEOUT);

			String listaSinonimi = getPossibiliTraduzioni(doc);

			Element elements = doc.getElementById(EXAMPLES_ID);
			elements.getElementsByClass(EXAMPLE_CLASS).stream()
					.limit(NUM_EXAMPLES)
					.forEach(example -> {
						String traduzione = getEsempioTradotto(example).text();
						String contenuto = getEsempioContenuto(example).text();

						AbstractAnkiCard card = new SimpleAnkiCard();
						CardDecorator.addWordLearnedToFront(card, word);
						CardDecorator.addTranslationToFront(card, traduzione);
						CardDecorator.addContenutoToBack(card, contenuto);
						CardDecorator.addParoleTradotteToBack(card, listaSinonimi);
						cardList.add(card);

					});
			return cardList;


		} catch (MalformedURLException | HttpStatusException ex) {
			log.error("Error while scraping: " + ex);
		} catch (IOException ioEx) {
			log.error("Error with I/O: " + ioEx);
		}

		return cardList;
	}




	/** Prendo tutte le traduzioni possibili (limitate d un numero) per una sola parola **/
	private String getPossibiliTraduzioni(Document doc) {
		try {
			return "Possibili traduzioni: "  +
					doc.getElementById(TRANSLATION_CONTENT_ID)
						.getElementsByTag(ANCHOR_TAG).stream()
						.limit(NUM_TRANSLATIONS)
						.map(Element::text)
						.collect(Collectors.joining(", "));

		} catch (NullPointerException npex) {
			return "";
		}
	}

	private Element getEsempioContenuto(Element example) {
		return getContents(example, CONTENT_CLASS);
	}

	private Element getEsempioTradotto(Element example) {
		return getContents(example, TRADUCT_CLASS);
	}


	private Element getContents(Element example, String cssClass) {
		Elements content = example.getElementsByClass(cssClass);
		if (content.isEmpty())
			throw new RuntimeException("Content not found for elements: " + example);
		if (content.size() > 1)
			throw new RuntimeException("Esistono piu' versioni per l'esempio: " + example);

		return content.get(0);
	}


}
