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

    public void createClozeFlashcards(String input, String output) throws Exception {
        List<String> wordList = getWordListFromFile(input);
        int numWords = 0;

        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"))) {

            for (String word : wordList) {
                Map<String, String> originalMap = definitionPage.getWordDefinition(word);
                Map<String, String> clozeMap = clozeEngine.createClozeMap(originalMap, word);

                for (Map.Entry<String, String> cloze : clozeMap.entrySet()) {
                    IAnkiCard card = webCardDecorator.create(cloze.getValue(),
                            word, originalMap.get(cloze.getKey()), cloze.getKey());
                    bos.write(card.toString());
                }

                logNumberOfWords(numWords++);

                Thread.sleep(TIME_SLEEP);
            }
        }
    }
}
