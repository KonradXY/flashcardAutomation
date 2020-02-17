package main.java.model.webcrawler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.webscraper.reverso.ReversoSpanishScraper;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;
import main.java.webscraper.wordreference.WordReferenceSynonimsPage;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static main.java.card_decorators.AbstractCardDecorator.*;

@Singleton
public class SpanishGeneralWebCrawler implements IWebCrawler {


    private final ReversoSpanishScraper reversoCrawler;
    private final WordReferenceDefinitionPage definitionPageWR;
    private final WordReferenceSynonimsPage synonimsPageWR;

    @Inject
    public SpanishGeneralWebCrawler(ReversoSpanishScraper reversoCrawler, WordReferenceDefinitionPage wordReferenceDefinitionPage, WordReferenceSynonimsPage wordReferenceSynonimsPage) {
        this.reversoCrawler = reversoCrawler;
        this.definitionPageWR = wordReferenceDefinitionPage;
        this.synonimsPageWR = wordReferenceSynonimsPage;
    }

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
        definitionPageWR.scrapePageWithWord(Collections.emptyList(), word);
        synonimsPageWR.scrapePageWithWord(Collections.emptyList(), word);

        Map<String, String> definizioniMap = definitionPageWR.getWordDefinition(word);
        List<String> synonims = synonimsPageWR.getSynonimsFromWord(word);

        List<IAnkiCard> cards = new ArrayList<>();
        reversoCrawler.scrapePageWithWord(cards, word);

        for (IAnkiCard card : cards) {
            addDefinizioneToBack(card, definizioniMap);
            addSinonimiToBack(card, synonims);
        }

        return cards;
    }


    private void addDefinizioneToBack(IAnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getUnorderedListTag().addClass(DEFINIZIONI_CLASS);
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyLeftFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text(DEFINIZIONI_CLASS));
        card.getBack().appendChild(definizioniList);

    }

    protected void addSinonimiToBack(IAnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getUnorderedListTag().addClass(SINONIMI_CLASS);
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyLeftFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text(SINONIMI_CLASS));
        card.getBack().appendChild(listaSinonimi);

    }
}
