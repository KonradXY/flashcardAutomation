package main.java.model.languageLearning;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.languageLearning.LanguageLearningAnkiCard.PracticeMakesPerfectEnum;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class LanguageLearningParser implements IParser {
	
	private final static Logger log = Logger.getLogger(LanguageLearningParser.class);
	
	@Override
	public List<IAnkiCard> parse(Map<Path, String> input) {
		List<IAnkiCard> cardList = new ArrayList<>();

		for (Map.Entry<Path, String> file : input.entrySet()) {
			
			String fileName = file.getKey().toString().trim();
			
			if 		(fileName.endsWith("grammatica.txt"))
				addParsedGrammaticaCard(cardList, file.getValue());
			
			else if (fileName.endsWith("vocabolario.txt"))
				addParsedVocabolarioCard(cardList, file.getValue());

			else if (fileName.endsWith("traduzioni.txt"))
				addParsedTraduzioniCard(cardList, file.getValue());	
			
			else if (fileName.endsWith("parole.txt"))	// TODO - questo in teoria e' il simple parser (da customizzare con eng/esp o viceversa)
				throw new UnsupportedOperationException("TODO - funzione da fare !");
			
			else {
				log.info("Trovato file non contemplato per il parser LanguageLearning: " + file.getKey() 
				+ "\nil file verrà scartato. ");
				continue;
			}

		}
		return cardList;
	}
	
	private void addParsedGrammaticaCard(List<IAnkiCard> cardList, String grammaticaInput) {
		String[] splitted = grammaticaInput.split(PIPE_SEPARATOR);
		int length = (splitted.length%2 == 0 ? splitted.length : splitted.length -1);
		for (int i = 0; i < length; i+=2) {
			cardList.add(new LanguageLearningAnkiCard(splitted[i], splitted[i+1], PracticeMakesPerfectEnum.GRAMMATICA));
		}
	}

	private void addParsedVocabolarioCard(List<IAnkiCard> listCard, String input) {
		addNestedParsedCard(listCard, input, PracticeMakesPerfectEnum.VOCABOLARIO);
	}

	private void addParsedTraduzioniCard(List<IAnkiCard> listCard, String input) {
		addParsedCard(listCard, input, PracticeMakesPerfectEnum.TRADUZIONE);
	}
	
	private <T> void addParsedCard(List<IAnkiCard> listCard, String input, PracticeMakesPerfectEnum cardKind) {

		String qa[] = parseDomandeRisposte(input);

		Map<String, String> domandeMap = new HashMap<>();
		Stream.of(splitString(qa[DOMANDE_INDEX], IParser.PIPE_SEPARATOR)).forEach(
				it -> addExcercise(it, domandeMap));

		Map<String, String> risposteMap = new HashMap<>();
		Stream.of(splitString(qa[1], IParser.PIPE_SEPARATOR)).forEach(
				it -> addExcercise(it, risposteMap));

		boolean allKeysAremMatched = true;
		for (String domKey : domandeMap.keySet()) {
			allKeysAremMatched &= checkKeyMap(domKey, domandeMap);
		}

		if (!allKeysAremMatched) {
			throw new RuntimeException("Mancano delle chiavi all'interno degli esercizi ");
		}

		for (String domKey : domandeMap.keySet()) {
			listCard.add(new LanguageLearningAnkiCard(
					domandeMap.get(domKey),
					risposteMap.get(domKey),
					cardKind
			));
		}

	}
	
	
	
	private void addNestedParsedCard(List<IAnkiCard> listCard, String input, PracticeMakesPerfectEnum cardKind) {

		Map<String, Map<String, String>> domandeMap = new HashMap<>();
		Map<String, Map<String, String>> risposteMap = new HashMap<>();

		String qa[] = parseDomandeRisposte(input);

		Stream.of(splitString(qa[0], IParser.PIPE_SEPARATOR)).forEach(it -> {
			addNestedExcercise(it, domandeMap);
		});

		Stream.of(splitString(qa[1], IParser.PIPE_SEPARATOR)).forEach(it -> {
			addNestedExcercise(it, risposteMap);
		});

		LanguageLearningAnkiCard card;
		for (String domKey : domandeMap.keySet()) {
			checkKeyMap(domKey, risposteMap);

			for (String innerDomKey : domandeMap.get(domKey).keySet()) {
				checkKeyMap(innerDomKey, risposteMap.get(domKey));

				card = new LanguageLearningAnkiCard(
						domandeMap.get(domKey).get(innerDomKey),
						risposteMap.get(domKey).get(innerDomKey),
						cardKind);
				listCard.add(card);
			}
		}

	}
	
	
	private void addNestedExcercise(String str, Map<String, Map<String, String>> map) {
		addToNestedMapsWithRegexChecking(str, map, NUM_EX_PATTERN, SINGLE_EX_PATTERN);
	}
	
	private void addExcercise(String str, Map<String, String> map) {
		addToMapWithRegexChecking(str, map, NUM_EX_PATTERN);
	}

	private Map<String, String> addToMapWithRegexChecking(String str, Map<String, String> map, Pattern regex) {
		Matcher matcher = regex.matcher(str);
		while (matcher.find()) {
			String key = matcher.group(0);
			map.put(key, str.replaceAll(key, ""));
		}
		return map;
	}
	
	private void addToNestedMapsWithRegexChecking(String str, Map<String, Map<String, String>> map, Pattern outerRegex, Pattern innerRegex) {
		Matcher matcher = outerRegex.matcher(str);
		while (matcher.find()) {
			Map<String, String> innerMap = new HashMap<>();
			String key = matcher.group(0);
			String value = str.replaceAll(key, "");

			Matcher innerMatcher = innerRegex.matcher(value);
			List<String> numEx = new ArrayList<>();
			while (innerMatcher.find()) 
				numEx.add(innerMatcher.group(0));

			for (int i = 0; i < numEx.size()-1; i++) {
				String innerValue = value.substring(value.indexOf(numEx.get(i)), value.indexOf(numEx.get(i+1)));
				innerMap.put(numEx.get(i), innerValue.replaceAll(numEx.get(i), ""));
			}
			String lastKey = numEx.get(numEx.size()-1);
			int indexOf = value.indexOf(numEx.get(numEx.size()-1));
			String lastValue = value.substring(indexOf);
			innerMap.put(lastKey, lastValue.replaceAll(lastKey, ""));

			map.put(key, innerMap);

		}

	}
	
	private <K, V> boolean checkKeyMap(K key, Map<K, V> map) {
		if (!map.containsKey(key)) {
			log.error("Non e' stata trovata la coppia di chiavi D/R per la chiave: " + key);
			return false;
		}
		return true;
	}
	
	private String[] parseDomandeRisposte(String input) {
		String[] qa = splitString(input, QA_SEPARATOR);
		if (qa.length != 2) throw new RuntimeException("File non parsato correttamente. Manca il campo: " + QA_SEPARATOR);
		return qa;
	}
	
	// TODO - verificare perch� lancia l'errore per lo splitter "\\|"
	private String[] splitString(String str, String splitter) {
		//		if (!str.contains(splitter)) 
		//			throw new RuntimeException("Lo splitter: " + splitter + " non � presente nella stringa: " + str);
		return str.split(splitter);
	}


}
