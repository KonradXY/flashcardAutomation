package main.java.facade;

import static main.java.modelDecorator.AbstractCardDecorator.*;
import static main.java.webcrawlers.AbstractWebCrawler.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.inject.Singleton;
import main.java.model.AnkiCard;
import main.java.webcrawlers.ReversoSpanishCrawler;
import main.java.webcrawlers.WordReferenceCrawler;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import main.java.contracts.IAnkiCard;
import main.java.modelDecorator.WebParsedClozedCardDecorator;
import org.jsoup.nodes.Element;

@Singleton
public class LanguageLearningFacade {

	private static final Logger log = Logger.getLogger(LanguageLearningFacade.class);
	private static final WebParsedClozedCardDecorator webCardDecorator = new WebParsedClozedCardDecorator();
	
	private final ReversoSpanishCrawler reversoCrawler;
	private final WordReferenceCrawler wordReferenceCrawler;
	

	@Inject
	LanguageLearningFacade(ReversoSpanishCrawler reversoCrawler, WordReferenceCrawler wordReferenceCrawler ) {
		this.reversoCrawler = reversoCrawler;
		this.wordReferenceCrawler = wordReferenceCrawler;
	}

	// TODO - tutte queste alla fine diventeranno delle strategy da wrappare all'interno della scrittura (un template o uno strategy pattern praticamente)
	// TODO - bisogna rivedere pratiacmente tutta sta parte (soprattutto se penso di combinarla con il parsing di codice da siti diversi)
	public void createDefinitionFlashcards(String inputFile, String outputFile) throws Exception {
		int numWords = 0;

		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

			List<String> wordList = getWordListFromFile(inputFile);
			IAnkiCard card;

			for (String word : wordList) {
				wordReferenceCrawler.scrapeSpanishItalianTranslationPage(word);

				writeCard(createSimpleDefinitionCard(word), bos);
				writeCard(createReverseDefinitionCard(word), bos);

				numWords+=2;
			}
		}

		log.info("effettuata la creazione di " + numWords + " carte");

	}

	private IAnkiCard createSimpleDefinitionCard(String word) {
		Map<String, String> traduzioni = wordReferenceCrawler.getWordTranslation(word);

		IAnkiCard card = new AnkiCard();
		addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));

		for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
			addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass("traduzione"));
		}

		Optional<Element> tip = wordReferenceCrawler.getWordTips(word);
		if (tip.isPresent()) {
			addContentToBack(card, tip.get().text(), getParagraphTag().addClass("tip"));
		}

		return card;
	}

	private IAnkiCard createReverseDefinitionCard(String word) {
		IAnkiCard card = new AnkiCard();
		Map<String, String> traduzioni = wordReferenceCrawler.getWordTranslation(word);
		if (!traduzioni.isEmpty()) 	{

			addContentToFront(card, traduzioni.values().iterator().next(), getParagraphTag().addClass("traduzione"));

			for (Map.Entry<String, String> entry : traduzioni.entrySet()) {
				addContentToBack(card, entry.getKey() + " - " + entry.getValue(), getParagraphTag().addClass("traduzione"));
			}
		}


		return card;
	}

	public void createFlashcard(String inputFile, String outputFile) throws Exception {
		int numWords = 0;
		List<String> wordList = getWordListFromFile(inputFile);
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

				for (String word : wordList) {

					wordReferenceCrawler.scrapeSpanishDefinitionWord(word);
					wordReferenceCrawler.scrapeSpanishSynonimsPage(word);

					Map<String, String> definizioniMap = wordReferenceCrawler.getWordDefinition(word);
					List<String> synonims = wordReferenceCrawler.getSynonimsFromWord(word);
					List<IAnkiCard> cards = reversoCrawler.getExamplesCardFromWord(word);

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
				Map<String, String> originalMap = wordReferenceCrawler.getWordDefinition(word);
				Map<String, String> clozeMap = createClozeMap(originalMap, word);

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

	private Map<String, String> createClozeMap(Map<String, String> map, String word) {
		Map<String, String> clozeMap = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		return 	clozeMap.entrySet().stream()
					.filter(valueNotEmpty)
					.map(it -> clozifyText(it, word))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	private Predicate<Map.Entry<String, String>> valueNotEmpty = (it -> !it.getValue().trim().isEmpty());

	private Map.Entry<String, String> clozifyText(Map.Entry<String, String> entry, String word) {
		String value = entry.getValue();
		value = clozifyWord(value, word);
		entry.setValue(value);
		return entry;
	}


	private String clozifyWord(String text, String word) {
		String wordToBeClozed = getMostCloseWord(text, word);
		int charBuffer = (wordToBeClozed.length() > 2 ? wordToBeClozed.length() -2 : 2);
		String clozeChar = CharBuffer.allocate(charBuffer).toString().replace('\0','_');
		clozeChar = wordToBeClozed.charAt(0) +  clozeChar + wordToBeClozed.charAt(wordToBeClozed.length()-1);
		return text.replace(wordToBeClozed, clozeChar);
	}

	private String getMostCloseWord(String text, String word) {
		String[] words = text.split(" ");
		int minDistance = 100;
		int index = 0;
		for (int i = 0; i < words.length; i++) {
			int distance = LevenshteinDistance.getDefaultInstance().apply(word, words[i]);
			if (distance < minDistance){
				minDistance = distance;
				index = i;
			}
		}

		return words[index];
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
