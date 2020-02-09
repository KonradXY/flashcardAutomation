package main.java.utils;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.STOPWORD_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

@Singleton
public class WebCrawlerEngineUtils {

	public static final Path SPANISH_STOPWORDS = Paths.get(INPUT_DIR + STOPWORD_PATH + "stopwords-es.txt");
	
	private static final Logger log = Logger.getLogger(WebCrawlerEngineUtils.class);
	
	private static Set<String> spanishStopwords;

	// TODO - da testare
	public Set<String> getSpanishStopwords() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(SPANISH_STOPWORDS.toString()), "UTF-8"))) {
			return br.lines().collect(Collectors.toSet());
		} catch (IOException ex) {
			log.error("Errore nel caricamento delle spanish stopwords", ex);
			throw new RuntimeException(ex);
		}
	}
	
	
	// TODO - da testare
	public void checkForStopWords(Map<Path, List<String>> content) {
		
		if (spanishStopwords == null)
			spanishStopwords = getSpanishStopwords();
		
    	content.values().stream().forEach(words -> {
    		words.stream().forEach(word -> {
    			List<String> tokens = Arrays.asList(word.split(" "));
    			tokens.forEach(t -> {
    				if (spanishStopwords.contains(t)) {
    					tokens.remove(t);
    				}
    			});
    			
    			word = tokens.stream().collect(Collectors.joining(" "));
    			
    		});
    	});
    }
}
