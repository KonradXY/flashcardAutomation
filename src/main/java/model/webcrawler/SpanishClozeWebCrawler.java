package main.java.model.webcrawler;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import main.java.card_decorators.WebParsedClozedCardDecorator;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.utils.ClozeEngine;
import main.java.webpages.wordreference.WordReferenceDefinitionPage;

@Singleton
public class SpanishClozeWebCrawler implements IWebCrawler {

    private final ClozeEngine clozeEngine;
    private final WordReferenceDefinitionPage definitionPage;
    private final WebParsedClozedCardDecorator clozedCardDecorator;

    @Inject
    public SpanishClozeWebCrawler(ClozeEngine clozeEngine, WordReferenceDefinitionPage wordReferenceDefinitionPage, WebParsedClozedCardDecorator webCardDecorator) {
        this.clozeEngine = clozeEngine;
        this.definitionPage = wordReferenceDefinitionPage;
        this.clozedCardDecorator = webCardDecorator;
    }

    @Override
    public List<IAnkiCard> createFlashcards(String word) {
    	
    	throw new UnsupportedOperationException("Il cloze engine e' da rifare !");
    	
//        Element originalMap = definitionPage.getWordDefinitions();
//        Map<String, String> clozeMap = clozeEngine.createClozeMap(originalMap, word);
//        IAnkiCard card = null;
//
//        for (Map.Entry<String, String> cloze : clozeMap.entrySet()) {
//            card = clozedCardDecorator.create(cloze.getValue(), word, originalMap.get(cloze.getKey()), cloze.getKey());
//        }
//
//        return Arrays.asList(card);
    }

}
