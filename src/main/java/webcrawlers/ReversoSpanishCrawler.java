package main.java.webcrawlers;

import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.utils.WebCrawlerProperties.NUM_EXAMPLES;
import static main.java.utils.WebCrawlerProperties.NUM_TRANSLATIONS;
import static main.java.utils.WebCrawlerProperties.REVERSO_ESP_ITA_TRANSLATION_PAGE_URL;
import static main.java.modelDecorator.AbstractCardDecorator.*;

@Singleton
public class ReversoSpanishCrawler extends AbstractWebCrawler {

    private final static Logger log = Logger.getLogger(ReversoSpanishCrawler.class);
    private static final String EXAMPLES_ID = "examples-content";
    private static final String TRANSLATION_CONTENT_ID = "translations-content";
    private static final String EXAMPLE_CLASS = "example";
    private static final String CONTENT_CLASS = "src ltr";
    private static final String TRADUCT_CLASS = "trg ltr";

    private static final String ANCHOR_TAG = "a";

    public List<IAnkiCard> getReversoExamplesFromWord(String word) {
        List<IAnkiCard> cardList = new ArrayList<>();

        try {
            doc = Jsoup.connect(getUrlAsString(REVERSO_ESP_ITA_TRANSLATION_PAGE_URL, word)).userAgent(USER_AGENT).timeout(TIMEOUT).get();
            Element elements = doc.getElementById(EXAMPLES_ID);
            elements.getElementsByClass(EXAMPLE_CLASS).stream()
                    .limit(NUM_EXAMPLES)
                    .forEach(example -> {
                        String traduzione = getEsempioTradotto(example).text();
                        String contenuto = getEsempioContenuto(example).text();

                        IAnkiCard card = new AnkiCard();
                        addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));
                        addContentToFront(card, traduzione, getParagraphTag().addClass("traduzione"));
                        addContentToBack(card, contenuto, getParagraphTag().addClass("contenuto"));

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


    /**
     * Prendo tutte le traduzioni possibili (limitate d un numero) per una sola parola
     **/
    private String getPossibiliTraduzioni(Document doc) {
        try {
            return "Possibili traduzioni: " +
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
