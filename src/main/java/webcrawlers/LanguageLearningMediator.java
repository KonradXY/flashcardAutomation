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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.abstractModel.AbstractAnkiCard;
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
	
	public void parse(String inputFile, String outputFile) throws Exception {
		List<String> wordList = getWordListFromFile(inputFile);

		int numWords = 0;


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
	
	
	private void writeCards(List<AbstractAnkiCard> cards, BufferedWriter bos)throws IOException {
		for (AbstractAnkiCard card : cards) {
			if (!card.getFrontHtml().text().trim().isEmpty())
				bos.write(card.toElementString());
		}

		bos.flush();
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


}