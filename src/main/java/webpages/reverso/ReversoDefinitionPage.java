package main.java.webpages.reverso;

import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.webpages.AbstractPage;

public class ReversoDefinitionPage extends AbstractPage {

    private static final String REVERSO_ESP_ITA_TRANSLATION_PAGE_URL = "http://context.reverso.net/traduzione/spagnolo-italiano/";

    private static final String EXAMPLES_ID = "examples-content";
    private static final String EXAMPLE_CLASS = "example";
    private static final String CONTENT_CLASS = "src ltr";
    private static final String TRADUCT_CLASS = "trg ltr";

    private Document reversoPage;

    @Override
    public void scrapePageWithWord(String word) {
        reversoPage = scrapePage(REVERSO_ESP_ITA_TRANSLATION_PAGE_URL, word);
    }

    public Map<String, String> getTraduzioni() {
        if (reversoPage == null) {
            throw new IllegalStateException("reversoPage cannot be null while creating cards !");
        }

        Element elements = reversoPage.getElementById(EXAMPLES_ID);
        return elements.getElementsByClass(EXAMPLE_CLASS).stream()
                .limit(MAX_NUM_EXAMPLES_PER_WORD)
                .collect(Collectors.toMap(it -> getEsempioTradotto(it).text(), it -> getEsempioContenuto(it).text()));
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
            throw new IllegalStateException("Content not found for elements: " + example);
        if (content.size() > 1)
            throw new IllegalStateException("Esistono piu' versioni per l'esempio: " + example);

        return content.get(0);
    }

    void setDefinitionPage(Document doc) {
        this.reversoPage = doc;
    }


}
