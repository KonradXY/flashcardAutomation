package main.java.model.webcrawler;

import static main.java.card_decorators.AbstractCardDecorator.applyLeftFormatRecursively;
import static main.java.card_decorators.AbstractCardDecorator.createSingleDefinizione;
import static main.java.card_decorators.AbstractCardDecorator.getBoldParagraphTag;
import static main.java.card_decorators.AbstractCardDecorator.getListItemTag;
import static main.java.card_decorators.AbstractCardDecorator.getNewLineTag;
import static main.java.card_decorators.AbstractCardDecorator.getUnorderedListTag;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.enums.CssClass;
import main.java.webpages.reverso.ReversoDefinitionPage;
import main.java.webpages.wordreference.WordReferenceDefinitionPage;
import main.java.webpages.wordreference.WordReferenceSynonimsPage;

@Singleton
public class SpanishGeneralWebCrawler implements IWebCrawler {


    private final ReversoDefinitionPage reversoCrawler;
    private final WordReferenceDefinitionPage definitionPageWR;
    private final WordReferenceSynonimsPage synonimsPageWR;

    @Inject
    public SpanishGeneralWebCrawler(ReversoDefinitionPage reversoCrawler, WordReferenceDefinitionPage wordReferenceDefinitionPage, WordReferenceSynonimsPage wordReferenceSynonimsPage) {
        this.reversoCrawler = reversoCrawler;
        this.definitionPageWR = wordReferenceDefinitionPage;
        this.synonimsPageWR = wordReferenceSynonimsPage;
    }

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
    	
    	throw new UnsupportedOperationException("Questo generatore e' da rifare !");
    	
//        definitionPageWR.scrapePageWithWord(word);
//        synonimsPageWR.scrapePageWithWord(word);
//        reversoCrawler.scrapePageWithWord(word);
//
//        Map<String, String> definizioniWR = definitionPageWR.getWordDefinitions();
//        List<String> synonims = synonimsPageWR.getSynonimsFromWord();
//        Map<String, String> definizioniReverso = reversoCrawler.getTraduzioni();
//
//
//        List<IAnkiCard> cardList = new ArrayList<>();
//        for (Map.Entry<String, String>  entry  : definizioniReverso.entrySet()) {
//            IAnkiCard card = new AnkiCard();
//            String traduzione = entry.getKey();
//            String contenuto = entry.getValue();
//            card.addContentToFront(word, getBoldParagraphTag().addClass("wordLearned"));
//            card.addContentToFront(traduzione, getParagraphTag().addClass("traduzione"));
//            card.addContentToBack(contenuto, getParagraphTag().addClass("contenuto"));
//            addDefinizioneToBack(card, definizioniWR);
//            addSinonimiToBack(card, synonims);
//
//            cardList.add(card);
//        }
//
//        return cardList;
    }


    private void addDefinizioneToBack(IAnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getUnorderedListTag().addClass(CssClass.DEFINIZIONI_CLASS.getValue());
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyLeftFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text(CssClass.DEFINIZIONI_CLASS.getValue()));
        card.getBack().appendChild(definizioniList);

    }

    protected void addSinonimiToBack(IAnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getUnorderedListTag().addClass(CssClass.SINONIMI_CLASS.getValue());
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyLeftFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text(CssClass.SINONIMI_CLASS.getValue()));
        card.getBack().appendChild(listaSinonimi);

    }
}
