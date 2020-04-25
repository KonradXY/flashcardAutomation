package main.java.model.webcrawler;

import static main.java.card_decorators.AbstractCardDecorator.getBoldParagraphTag;
import static main.java.card_decorators.AbstractCardDecorator.getParagraphTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.model.AnkiCard;
import main.java.webpages.wordreference.WordReferenceTranslationPage;

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
        Map<String, Element> traduzioni = translationPageWR.getWordTranslation();

        IAnkiCard card = new AnkiCard();
        card.addContentToFront(word, getBoldParagraphTag().addClass("wordLearned"));

        for (Map.Entry<String, Element> entry : traduzioni.entrySet()) {
            card.addContentToBack(entry.getValue());
        }

        return card;
    }

    private IAnkiCard createReverseDefinitionCard() {
        Map<String, Element> traduzioni = translationPageWR.getWordTranslation();
        
        IAnkiCard card = new AnkiCard();
        if (!traduzioni.isEmpty()) {
            card.addContentToFront(traduzioni.values().iterator().next());

            for (Map.Entry<String, Element> entry : traduzioni.entrySet()) {
                card.addContentToBack(entry.getValue().html(), getParagraphTag().addClass(TRADUZIONE_CLASS));
            }
        }
        return card;
    }
}
