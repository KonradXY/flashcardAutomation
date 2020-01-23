package main.java.service;

import static main.java.modelDecorator.AbstractCardDecorator.*;
import static main.java.webcrawlers.AbstractWebCrawler.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.inject.Singleton;
import main.java.model.AnkiCard;
import main.java.utils.ClozeEngine;
import main.java.webcrawlers.ReversoSpanishCrawler;
import main.java.webcrawlers.wordreference.WordReferenceDefinitionPage;
import main.java.webcrawlers.wordreference.WordReferenceSynonimsPage;
import main.java.webcrawlers.wordreference.WordReferenceTranslationPage;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import main.java.contracts.IAnkiCard;
import main.java.modelDecorator.WebParsedClozedCardDecorator;
import org.jsoup.nodes.Element;

@Singleton
public class SpanishWebService {

    private static final Logger log = Logger.getLogger(SpanishWebService.class);
    private static final WebParsedClozedCardDecorator webCardDecorator = new WebParsedClozedCardDecorator();

    private final ReversoSpanishCrawler reversoCrawler;
    private final WordReferenceDefinitionPage definitionPageWR;
    private final WordReferenceSynonimsPage synonimsPageWR;
    private final WordReferenceTranslationPage translationPageWR;

    private final ClozeEngine clozeEngine;


    @Inject
    SpanishWebService(ReversoSpanishCrawler reversoCrawler, WordReferenceTranslationPage translationPageWR, WordReferenceSynonimsPage synonimsPageWR, WordReferenceDefinitionPage definitionPageWR, ClozeEngine clozeEngine) {
        this.reversoCrawler = reversoCrawler;
        this.definitionPageWR = definitionPageWR;
        this.synonimsPageWR = synonimsPageWR;
        this.translationPageWR = translationPageWR;
        this.clozeEngine = clozeEngine;
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

    /**
     * @param inputFile
     * @param outputFile
     * @throws Exception
     */
    public void createFlashcard(String inputFile, String outputFile) throws Exception {
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

    public void createClozeFlashcards(String input, String output) throws Exception {
        List<String> wordList = getWordListFromFile(input);
        int numWords = 0;

        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"))) {

            for (String word : wordList) {
                Map<String, String> originalMap = definitionPageWR.getWordDefinition(word);
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

    private void writeCards(List<IAnkiCard> cards, BufferedWriter bos) throws IOException {
        for (IAnkiCard card : cards) {
            writeCard(card, bos);
        }

        bos.flush();
    }

    private void writeCard(IAnkiCard card, BufferedWriter bos) throws IOException {
        if (!card.getFront().text().trim().isEmpty()) {
            bos.write(card.toString());
            bos.flush();
        }

    }

    private List<String> getWordListFromFile(String inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
        List<String> wordsList = br.lines()
                .map(String::trim)
                //.limit(5)
                .collect(Collectors.toList());
        br.close();
        return wordsList;
    }


    private void logNumberOfWords(int number) {
        if (number % LOG_COUNTER == 0) {
            log.info("Numero di esempi parsati: " + number * MAX_NUM_EXAMPLES_PER_WORD);
        }
    }


}
