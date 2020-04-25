package main.java.model.webcrawler;

import static main.java.card_decorators.AbstractCardDecorator.getBoldParagraphTag;
import static main.java.card_decorators.AbstractCardDecorator.getParagraphTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.enums.CssClass;
import main.java.model.AnkiCard;
import main.java.webpages.wordreference.WordReferenceDefinitionPage;
import main.java.webpages.wordreference.WordReferenceTranslationPage;

@Singleton
public class SpanishSimpleDefinitionWebCrawler implements IWebCrawler {

	private static final Logger log = Logger.getLogger(SpanishSimpleDefinitionWebCrawler.class);
	
	private final WordReferenceTranslationPage translationPageWR;
	private final WordReferenceDefinitionPage definitionPageWR;
	
	private Map<String, Element> traduzioni;
	private Element definizione;
	
	@Inject
	public SpanishSimpleDefinitionWebCrawler(WordReferenceTranslationPage translationPageWR, WordReferenceDefinitionPage definitionPageWR) {
		this.translationPageWR = translationPageWR;
		this.definitionPageWR = definitionPageWR;
	}

	@Override
	public List<IAnkiCard> createFlashcards(String word) {
		if (!loadTraduzioni(word) || !loadDefinizione(word))
			return Collections.emptyList();
		
		return Arrays.asList(createSimpleDefinitionCard(word), createReverseDefinitionCard());

	}

	private boolean loadDefinizione(String word) {
		definitionPageWR.scrapePageWithWord(word);
		definizione = definitionPageWR.getWordDefinitions();
		if (definizione == null || !definizione.hasText()) {
			log.info("non è stata trovata alcuna definizione per la parola: " + word);
			return false;
		}
		return true;
	}

	private boolean loadTraduzioni(String word) {
		translationPageWR.scrapePageWithWord(word);
		traduzioni = translationPageWR.getWordTranslation(); 
		if (traduzioni == null || traduzioni.isEmpty()) {
			log.info("non è stata trovata nessuna traduzione per la parola: " + word);
			return false;
		}
		
		return true;
	}

	private IAnkiCard createSimpleDefinitionCard(String word) {
		IAnkiCard card = new AnkiCard();
		card.addContentToFront(word, getBoldParagraphTag().addClass(CssClass.WORD_LEARNED.getValue()));
		card.addContentToBack(translationPageWR.getFirstValueFromMap(traduzioni));
		
		card.addContentToBack(new Element(Tag.valueOf("br"), ""));
		
		card.addContentToBack(definizione);
		return card;
	}

	private IAnkiCard createReverseDefinitionCard() {
		IAnkiCard card = new AnkiCard();
		card.addContentToFront(translationPageWR.getFirstTranslationFromList(translationPageWR.getFirstValueFromMap(traduzioni)).addClass(CssClass.TRADUZIONE_CLASS.getValue()));
		card.addContentToBack(translationPageWR.getFirstKeyFromMap(traduzioni), getParagraphTag().addClass(CssClass.WORD_LEARNED.getValue()));
		
		card.addContentToBack(new Element(Tag.valueOf("br"), ""));
		
		card.addContentToBack(definizione);
		return card;
	}



}
