package main.java.webcrawlers;

import static main.java.utils.WebCrawlerProperties.LOG_COUNTER;
import static main.java.utils.WebCrawlerProperties.NUM_EXAMPLES;
import static main.java.utils.WebCrawlerProperties.TIME_SLEEP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;
import main.java.modelDecorator.WebParsedClozedCardDecorator;

public class LanguageLearningMediator {

	private static final Logger log = Logger.getLogger(LanguageLearningMediator.class);
	private static final WebParsedClozedCardDecorator webCardDecorator = new WebParsedClozedCardDecorator();
	
	private final ReversoSpanishCrawler reversoCrawler; 
	private final WordReferenceCrawler wordReferenceCrawler;
	

	@Inject
	public LanguageLearningMediator(
			 ReversoSpanishCrawler reversoCrawler,
			 WordReferenceCrawler wordReferenceCrawler ) {
		this.reversoCrawler = reversoCrawler;
		this.wordReferenceCrawler = wordReferenceCrawler;
	}

	public void createFlashcard(String inputFile, String outputFile) throws Exception {
		int numWords = 0;
		List<String> wordList = getWordListFromFile(inputFile);
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

			for (String word : wordList) {

				Map<String, String> definizioniMap = wordReferenceCrawler.getWordDefinitions(word);
				List<String> synonims = wordReferenceCrawler.getWordSynonims(word);

				List<AnkiCard> cards = reversoCrawler.getExamplesFromWord(word, definizioniMap, synonims);
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
				Map<String, String> originalMap = wordReferenceCrawler.getWordDefinitions(word);
				Map<String, String> clozeMap = createClozeMap(originalMap, word);
				List<String> synonims = wordReferenceCrawler.getWordSynonims(word);

				for (Map.Entry<String, String> cloze : clozeMap.entrySet()) {
					IAnkiCard card = webCardDecorator.create(cloze.getValue(), 
							word, originalMap.get(cloze.getKey()), cloze.getKey(), synonims);
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

	private void writeCards(List<AnkiCard> cards, BufferedWriter bos) throws IOException {
		for (AnkiCard card : cards) {
			writeCard(card, bos);
		}

		bos.flush();
	}

	private void writeCard(AnkiCard card, BufferedWriter bos) throws IOException {
		if (!card.getFront().text().trim().isEmpty()) {
			bos.write(card.toString());
			bos.flush();
		}

	}
	
	private List<String> getWordListFromFile(String inputFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
		List<String> wordsList = br.lines()
				.map(String::trim)
				.limit(1)
				.collect(Collectors.toList());
		br.close();
		return wordsList;
	}


	private void logNumberOfWords(int number) {
		if (number % LOG_COUNTER == 0) {
			log.info("Numero di esempi parsati: " + number * NUM_EXAMPLES);
		}
	}


}
