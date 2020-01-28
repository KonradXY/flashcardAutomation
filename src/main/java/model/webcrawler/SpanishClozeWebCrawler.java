package main.java.model.webcrawler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.modelDecorator.WebParsedClozedCardDecorator;
import main.java.utils.ClozeEngine;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static main.java.webscraper.AbstractWebScraper.TIME_SLEEP;

@Singleton
public class SpanishClozeWebCrawler implements IWebCrawler {

    private static final WebParsedClozedCardDecorator webCardDecorator = new WebParsedClozedCardDecorator();

    private final ClozeEngine clozeEngine;
    private final WordReferenceDefinitionPage definitionPage;

    @Inject
    public SpanishClozeWebCrawler(ClozeEngine clozeEngine, WordReferenceDefinitionPage wordReferenceDefinitionPage) {
        this.clozeEngine = clozeEngine;
        this.definitionPage = wordReferenceDefinitionPage;
    }

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
        Map<String, String> originalMap = definitionPage.getWordDefinition(word);
        Map<String, String> clozeMap = clozeEngine.createClozeMap(originalMap, word);
        IAnkiCard card = null;

        for (Map.Entry<String, String> cloze : clozeMap.entrySet()) {
            card = webCardDecorator.create(cloze.getValue(),
                    word, originalMap.get(cloze.getKey()), cloze.getKey());
        }

        return Arrays.asList(card);
    }

}
