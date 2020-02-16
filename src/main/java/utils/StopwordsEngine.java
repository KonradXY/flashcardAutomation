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
		int deckIndex = 0;
		for (Map.Entry<Path, List<String>> entry : content.entrySet()) {

			List<String> deck = entry.getValue();
			List<String> newDeck = new ArrayList<>();

			for (String card : deck) {
				List<String> tokens = new ArrayList<>(Arrays.asList(card.split(" ")));
				tokens.removeIf(it -> stopwordsSet.contains(it));
				String newCardContent = unifyTokens(tokens);

				if (!newCardContent.isEmpty()) {
					newDeck.add(newCardContent);
				}
			}
			content.put(entry.getKey(), newDeck);
			deckIndex++;
		}
	}

    private String unifyTokens(List<String> stringList) {
		stringList.removeIf(str -> str.trim().isEmpty());
		return stringList.stream().collect(Collectors.joining(" "));
	}


}
