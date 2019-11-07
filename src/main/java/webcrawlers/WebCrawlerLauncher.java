package main.java.webcrawlers;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.modelDecorator.CardDecorator;
import main.java.netutilities.CertificateManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static main.java.utils.Property.WEB_CRAWLER_DIR;
import static main.java.utils.WebCrawlerProperties.LOG_COUNTER;
import static main.java.utils.WebCrawlerProperties.NUM_EXAMPLES;
import static main.java.utils.WebCrawlerProperties.TIME_SLEEP;

public class WebCrawlerLauncher {

	private static final Logger log = Logger.getLogger(WebCrawlerLauncher.class);
	
	private static final String INPUT_FILE  	= INPUT_DIR + WEB_CRAWLER_DIR + "2000_parole_lista_2.txt";
	private static final String ES_STOPWORDS 	= INPUT_DIR + WEB_CRAWLER_DIR + "stopwords-es.txt";
	private static final String OUTPUT_FILE 	= OUTPUT_DIR+ WEB_CRAWLER_DIR + "scrapedList.txt";
	
	private static ReversoSpanishCrawler reversoCrawler = new ReversoSpanishCrawler();
	private static WordReferenceCrawler wordReferenceCrawler = new WordReferenceCrawler();

	public static void main(String[] args) throws Exception {

		long t = System.currentTimeMillis();
		int numEx = 0;

		CertificateManager.doTrustToCertificates();

		log.info("Start crawling");

		List<String> wordList = getWordListFromFile(INPUT_FILE);
//		List<String> wordList = Arrays.asList("alrededor", "hermoso", "feo");

		int numWords = 0;


		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE), "UTF-8"))) {

			for (String word : wordList) {

				Map<String, String> definizioniMap = wordReferenceCrawler.getWordDefinitions(word);
				List<String> synonims = wordReferenceCrawler.getWordSynonims(word);

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

		log.info("Parsing completed ! Time elapsed: " + (System.currentTimeMillis() - t) / 1000 + " sec");
		log.info("Numero di parole scartate: " + numEx);
		log.info("Numero parole tradotte: " + (wordList.size() - numEx));

	}
	
	private static void writeCards(List<AbstractAnkiCard> cards, BufferedWriter bos)throws IOException {
		for (AbstractAnkiCard card : cards) {
			if (!card.getFrontHtml().text().trim().isEmpty())
				bos.write(card.toElementString());
		}

		bos.flush();
	}
	
	private static List<String> getWordListFromFile(String inputFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
		List<String> wordsList = br.lines()
				.map(String::trim)
				.collect(Collectors.toList());
		br.close();
		return wordsList;
	}

	private Set<String> getSpanishStopwords() throws IOException{
		return getWordListFromFile(ES_STOPWORDS).stream().collect(Collectors.toSet());
	}
	
}
