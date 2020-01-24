package main.java.model.webcrawler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.model.AnkiCard;
import main.java.webscraper.wordreference.WordReferenceTranslationPage;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static main.java.modelDecorator.AbstractCardDecorator.*;
import static main.java.modelDecorator.AbstractCardDecorator.getParagraphTag;

@Singleton
public class SpanishDefinitionWebCrawler implements IWebCrawler {

    private static final Logger log = Logger.getLogger(SpanishDefinitionWebCrawler.class);

    private final WordReferenceTranslationPage translationPageWR;

    @Inject
    public SpanishDefinitionWebCrawler(WordReferenceTranslationPage translationPageWR) {
        this.translationPageWR = translationPageWR;
    }

    // TODO - qua dentro voglio semplicemente creare la lista di carte. La stampa dev'essere fatta da qualche altra parte

    public void createDefinitionFlashcards(String inputFile, String outputFile) throws Exception {
        int numWords = 0;

        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

            List<String> wordList = getWordListFromFile(inputFile);
            IAnkiCard card;

            for (String word : wordList) {
                translationPageWR.scrapePageWithWord(Collections.EMPTY_LIST, word);

                writeCard(createSimpleDefinitionCard(word), bos);
                writeCard(createReverseDefinitionCard(word), bos);

                numWords += 2;
            }
        }

        log.info("effettuata la creazione di " + numWords + " carte");

    }

    /**
     * @param word
     * @return flashcard con definizione e traduzione in italiano
     */
    private IAnkiCard createSimpleDefinitionCard(String word) {
        Map<String, String> traduzioni = translationPageWR.getWordTranslation(word);

        IAnkiCard card = new AnkiCard();
        addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));

        for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
            addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass("traduzione"));
        }

        Optional<Element> tip = translationPageWR.getWordTips(word);
        if (tip.isPresent()) {
            addContentToBack(card, tip.get().text(), getParagraphTag().addClass("tip"));
        }

        return card;
    }

    /**
     * @param word
     * @return flashcard con traduzione in italiano e parola in spagnolo piu' le varie definizioni possibili
     */
    private IAnkiCard createReverseDefinitionCard(String word) {
        IAnkiCard card = new AnkiCard();
        Map<String, String> traduzioni = translationPageWR.getWordTranslation(word);
        if (!traduzioni.isEmpty()) {

            addContentToFront(card, traduzioni.values().iterator().next(), getParagraphTag().addClass("traduzione"));

            for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
                addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass("traduzione"));
            }
        }


        return card;
    }
}
