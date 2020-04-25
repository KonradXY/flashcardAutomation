package main.java.model.webcrawler;

import static main.java.card_decorators.AbstractCardDecorator.getBoldParagraphTag;
import static main.java.card_decorators.AbstractCardDecorator.getParagraphTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IWebCrawler;
import main.java.enums.CssClass;
import main.java.model.AnkiCard;
import main.java.webpages.wordreference.WordReferenceTranslationPage;

@Singleton
public class SpanishSimpleDefinitionWebCrawler implements IWebCrawler {

	private final WordReferenceTranslationPage translationPageWR;
	private Map<String, Element> traduzioni;

	@Inject
	public SpanishSimpleDefinitionWebCrawler(WordReferenceTranslationPage translationPageWR) {
		this.translationPageWR = translationPageWR;
	}

	@Override
	public List<IAnkiCard> createFlashcards(String word) {
		translationPageWR.scrapePageWithWord(word);
		traduzioni = translationPageWR.getWordTranslation(); 

		if (traduzioni.isEmpty())
			return Collections.emptyList();

		return Arrays.asList(createSimpleDefinitionCard(word), createReverseDefinitionCard());

	}

	private IAnkiCard createSimpleDefinitionCard(String word) {
		IAnkiCard card = new AnkiCard();
		card.addContentToFront(word, getBoldParagraphTag().addClass(CssClass.WORD_LEARNED.getValue()));
		card.addContentToBack(translationPageWR.getFirstValueFromMap(traduzioni));
		return card;
	}

	private IAnkiCard createReverseDefinitionCard() {
		IAnkiCard card = new AnkiCard();
		card.addContentToFront(translationPageWR.getFirstTranslationFromList(translationPageWR.getFirstValueFromMap(traduzioni)).addClass(CssClass.TRADUZIONE_CLASS.getValue()));
		card.addContentToBack(translationPageWR.getFirstKeyFromMap(traduzioni), getParagraphTag().addClass(CssClass.WORD_LEARNED.getValue()));
		return card;
	}



}
