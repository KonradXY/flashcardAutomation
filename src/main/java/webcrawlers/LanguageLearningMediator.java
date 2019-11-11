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
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.java.baseModel.SimpleAnkiCard;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.baseModel.AbstractAnkiCard;
import main.java.modelDecorator.CardDecorator;

@Component
public class LanguageLearningMediator {
	
	private static final Logger log = Logger.getLogger(LanguageLearningMediator.class);
	
	private final ReversoSpanishCrawler reversoCrawler; 
	private final WordReferenceCrawler wordReferenceBean;
	
	public LanguageLearningMediator(
			@Autowired ReversoSpanishCrawler reversoCrawler,
			@Autowired WordReferenceCrawler wordReferenceCrawler ) {
		this.reversoCrawler = reversoCrawler;
		this.wordReferenceBean = wordReferenceCrawler;
	}

	public void createFlashcard(String inputFile, String outputFile) throws Exception {
		int numWords = 0;
		List<String> wordList = getWordListFromFile(inputFile);
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

			for (String word : wordList) {

				Map<String, String> definizioniMap = wordReferenceBean.getWordDefinitions(word);
				List<String> synonims = wordReferenceBean.getWordSynonims(word);

				List<AbstractAnkiCard> cards = reversoCrawler.getExamplesFromWord(word);

				for (AbstractAnkiCard card : cards) {
					CardDecorator.addDefinizioneToBack(card, definizioniMap);
					CardDecorator.addSinonimiToBack(card, synonims);
				}

				writeCards(cards, bos);
				numWords++;

				if (numWords % LOG_COUNTER == 0) {
					log.info("Numero di esempi parsati: " + numWords * NUM_EXAMPLES);
				}

				Thread.sleep(TIME_SLEEP);
			}
		}
	}

	public void createClozeFlashcards(String input, String output) throws Exception {
		List<String> wordList = getWordListFromFile(input);
		int numWords = 0;

		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"))) {

			for (String word : wordList) {
				Map<String, String> clozeMap = createClozeMap(wordReferenceBean.getClozeDefinitions(word), word);
				List<String> synonims = wordReferenceBean.getWordSynonims(word);

				for (Map.Entry<String, String> cloze : clozeMap.entrySet()) {
					AbstractAnkiCard card = createClozeAnkiCard(cloze.getValue(), word, cloze.getKey());
					CardDecorator.addSinonimiToBack(card, synonims);
					bos.write(card.toString());
					numWords++;
				}

				if (numWords % LOG_COUNTER == 0) {
					log.info("Numero di esempi parsati: " + numWords * NUM_EXAMPLES);
				}

				Thread.sleep(TIME_SLEEP);
			}
		}
	}

	private AbstractAnkiCard createClozeAnkiCard(String clozeText, String word, String wordDefinition) {
		AbstractAnkiCard card = new SimpleAnkiCard();
		CardDecorator.addTranslationToFront(card, clozeText);
		CardDecorator.addWordLearnedToBack(card, word);
		CardDecorator.addContenutoToBack(card, wordDefinition);
		return card;
	}


	private Map<String, String> createClozeMap(Map<String, String> map, String word) {
		Map<String, String> clozeMap = map.entrySet().stream()
								.filter(valueNotEmpty)
								.map(it -> clozifyText(it, word))
								.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return clozeMap;
	}
	
	private Predicate<Map.Entry<String, String>> valueNotEmpty = (it -> !it.getValue().trim().isEmpty());

	private Map.Entry<String, String> clozifyText(Map.Entry<String, String> entry, String word) {
		String value = entry.getValue();
		value = clozifyWord(value, word);
		entry.setValue(value);
		return entry;
	}


	private String clozifyWord(String text, String word) {
		String cloze = CharBuffer.allocate(word.length()).toString().replace('\0','_');
		return text.replace(word, cloze);
	}

	private void writeCards(List<AbstractAnkiCard> cards, BufferedWriter bos) throws IOException {
		for (AbstractAnkiCard card : cards) {
			writeCard(card, bos);
		}

		bos.flush();
	}

	private void writeCard(AbstractAnkiCard card, BufferedWriter bos) throws IOException {
		if (!card.getFrontHtml().text().trim().isEmpty()) {
			bos.write(card.toString());
			bos.flush();
		}

	}
	
	private List<String> getWordListFromFile(String inputFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
		List<String> wordsList = br.lines()
				.map(String::trim)
				.limit(5)	// TODO - eliminare !
				.collect(Collectors.toList());
		br.close();
		return wordsList;
	}


}
