package main.java.model.webcrawler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.webscraper.reverso.ReversoSpanishScraper;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;
import main.java.webscraper.wordreference.WordReferenceSynonimsPage;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static main.java.modelDecorator.AbstractCardDecorator.*;
import static main.java.webscraper.AbstractWebScraper.TIME_SLEEP;

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

    public void createGeneralFlashcards(String inputFile, String outputFile) throws Exception {
        int numWords = 0;
        List<String> wordList = getWordListFromFile(inputFile);
        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

            for (String word : wordList) {

                definitionPageWR.scrapePageWithWord(Collections.EMPTY_LIST, word);
                synonimsPageWR.scrapePageWithWord(Collections.EMPTY_LIST, word);

                Map<String, String> definizioniMap = definitionPageWR.getWordDefinition(word);
                List<String> synonims = synonimsPageWR.getSynonimsFromWord(word);

                List<IAnkiCard> cards = new ArrayList<>();
                reversoCrawler.scrapePageWithWord(cards, word);

                for (IAnkiCard card : cards) {
                    addDefinizioneToBack(card, definizioniMap);
                    addSinonimiToBack(card, synonims);
                }

                writeCards(cards, bos);

                logNumberOfWords(numWords++);
                Thread.sleep(TIME_SLEEP);
            }

        }
    }


    private void addDefinizioneToBack(IAnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getUnorderedListTag().addClass("definizioni");
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyLeftFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text("Definizioni"));
        card.getBack().appendChild(definizioniList);

    }

    protected void addSinonimiToBack(IAnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getUnorderedListTag().addClass("sinonimi");
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyLeftFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text("Sinonimi"));
        card.getBack().appendChild(listaSinonimi);

    }
}
