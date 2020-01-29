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
import java.util.*;

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

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
        translationPageWR.scrapePageWithWord(Collections.EMPTY_LIST, word);
        List<IAnkiCard> cardList = new ArrayList<>();
        cardList.add(createSimpleDefinitionCard(word));
        cardList.add(createReverseDefinitionCard(word));
        return cardList;
    }

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
