package main.java.model.webcrawler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.model.AnkiCard;
import main.java.webscraper.wordreference.WordReferenceTranslationPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.java.card_decorators.AbstractCardDecorator.addContentToBack;
import static main.java.card_decorators.AbstractCardDecorator.addContentToFront;
import static main.java.card_decorators.AbstractCardDecorator.getBoldParagraphTag;
import static main.java.card_decorators.AbstractCardDecorator.getParagraphTag;

@Singleton
public class SpanishSimpleDefinitionWebCrawler implements IWebCrawler {

    private final WordReferenceTranslationPage translationPageWR;

    @Inject
    public SpanishSimpleDefinitionWebCrawler(WordReferenceTranslationPage translationPageWR) {
        this.translationPageWR = translationPageWR;
    }

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
        translationPageWR.scrapePageWithWord(word);
        List<IAnkiCard> cardList = new ArrayList<>();
        cardList.add(createSimpleDefinitionCard(word));
        cardList.add(createReverseDefinitionCard());
        return cardList;
    }

    private IAnkiCard createSimpleDefinitionCard(String word) {
        Map<String, String> traduzioni = translationPageWR.getWordTranslation();

        IAnkiCard card = new AnkiCard();
        addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));

        for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
            addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass(TRADUZIONE_CLASS));
        }

        return card;
    }

    private IAnkiCard createReverseDefinitionCard() {
        IAnkiCard card = new AnkiCard();
        Map<String, String> traduzioni = translationPageWR.getWordTranslation();
        if (!traduzioni.isEmpty()) {

            addContentToFront(card, traduzioni.values().iterator().next(), getParagraphTag().addClass(TRADUZIONE_CLASS));

            for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
                addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass(TRADUZIONE_CLASS));
            }
        }
        return card;
    }
}
