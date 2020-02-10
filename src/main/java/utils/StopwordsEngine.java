package main.java.utils;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.STOPWORD_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

@Singleton
public class StopwordsEngine {

	public static final Path SPANISH_STOPWORDS = Paths.get(INPUT_DIR + STOPWORD_PATH + "stopwords-es.txt");
	
	private static final Logger log = Logger.getLogger(StopwordsEngine.class);
	
	private static Set<String> spanishStopwords;

	public Set<String> getSpanishStopwords() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(SPANISH_STOPWORDS.toString()), "UTF-8"))) {
			return br.lines().collect(Collectors.toSet());
		} catch (IOException ex) {
			log.error("Errore nel caricamento delle spanish stopwords", ex);
			throw new RuntimeException(ex);
		}
	}


	public void checkForSpanishStopWords(Map<Path, List<String>> content) {
		if (spanishStopwords == null)
			spanishStopwords = getSpanishStopwords();

		checkForStopWords(content, spanishStopwords);
	}
	
	
	public void checkForStopWords(Map<Path, List<String>> content, Set<String> stopwordsSet) {

		for (Map.Entry<Path, List<String>> entry : content.entrySet()) {
			List<String> deck = entry.getValue();
			Iterator<String> cardIterator = deck.iterator();
			int cardIndex = 0;
			while (cardIterator.hasNext()) {
				String cardValue = cardIterator.next();
				List<String> tokens = new ArrayList<>(Arrays.asList(cardValue.split(" ")));
				tokens.removeIf(it -> stopwordsSet.contains(it));

				log.info("Prima e Dopo: " + deck.get(cardIndex) + " - " + tokens.stream().collect(Collectors.joining(" ")));

				deck.set(cardIndex++, tokens.stream().collect(Collectors.joining((" "))).trim());
			}

			deck.removeIf(it -> it.isEmpty());
		}
    }


}
