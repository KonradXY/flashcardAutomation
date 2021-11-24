package main.java.model.parsers;


import static main.java.model.parsers.IParser.DOMANDE_INDEX;
import static main.java.model.parsers.IParser.NUM_EX_PATTERN;
import static main.java.model.parsers.IParser.PIPE_SEPARATOR;
import static main.java.model.parsers.IParser.QA_SEPARATOR;
import static main.java.model.parsers.IParser.RISPOSTE_INDEX;
import static main.java.model.parsers.IParser.SINGLE_EX_PATTERN;
import static main.java.model.parsers.IParser.SINGLE_EX_PATTERN_WITH_UNDERSCORE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import main.java.model.card.card_decorators.LeftFormatDecorator;
import main.java.model.card.AnkiCard;
import main.java.model.deck.AnkiDeck;
import main.java.model.card.LanguageLearningAnkiCard;

public enum TextParsingStrategy {

    PRACTICE_MAKES_PERFECT_GRAMMATICA {
        public void parseFile(AnkiDeck deck, String inputFile) {
            String[] splitted = inputFile.split(PIPE_SEPARATOR);
            int length = (splitted.length % 2 == 0 ? splitted.length : splitted.length - 1);
            for (int i = 0; i < length; i += 2) {
                deck.addCard(new LanguageLearningAnkiCard(splitted[i], splitted[i + 1], LanguageLearningAnkiCard.PracticeMakesPerfectEnum.GRAMMATICA));
            }
        }
    },

    PRACTICE_MAKES_PERFECT_VOCABOLARIO {
        public void parseFile(AnkiDeck deck, String inputFile) {
            addNestedParsedCard(deck, inputFile, LanguageLearningAnkiCard.PracticeMakesPerfectEnum.VOCABOLARIO);
        }
    },

    PRACTICE_MAKES_PERFECT_TRADUZIONI {
        public void parseFile(AnkiDeck deck, String inputFile) {
            addParsedCard(deck, inputFile, LanguageLearningAnkiCard.PracticeMakesPerfectEnum.TRADUZIONE);
        }
    },

    Q_A_PARSER {
        public void parseFile(AnkiDeck deck, String inputFile) {
            String[] splittedText = inputFile.split(PIPE_SEPARATOR);

            if (!entriesAreEven(splittedText))
                throw new RuntimeException("Le entry non sono pari ! Controllare che ogni entry abbia una domanda e una risposta !");

            for (int i = 0; i < splittedText.length; i += 2) {
                deck.addCard(new AnkiCard(splittedText[i + FIRST_FIELD],
                        splittedText[i + SECOND_FIELD]));
            }
        }
    },
    
    GENERATED_INDEX {
    	public void parseFile(AnkiDeck deck, String inputFile) {
    		addParsedCardWhileGeneratingIndex(deck, inputFile);
    	}
    };

    private final static Logger log = Logger.getLogger(TextParsingStrategy.class);


    // ======== class starts here !!!
    public static int FIRST_FIELD = 0;
    public static int SECOND_FIELD = 1;
    
    public static int generatedIndex = 0;

    public abstract void parseFile(AnkiDeck deck, String inputFile);

    boolean entriesAreEven(String[] entries) {
        return entries.length % 2 == 0;
    }
    
    void addParsedCardWhileGeneratingIndex(AnkiDeck deck, String input) {

        String qa[] = parseDomandeRisposte(input);

        Map<String, String> domandeMap = new LinkedHashMap<>();
        generatedIndex = 0;
        Stream.of(qa[DOMANDE_INDEX].split(IParser.PIPE_SEPARATOR)).forEach(
                it -> addExcerciseAndGenerateIndex(it, domandeMap));

        generatedIndex = 0;
        Map<String, String> risposteMap = new LinkedHashMap<>();
        Stream.of(qa[RISPOSTE_INDEX].split(IParser.PIPE_SEPARATOR)).forEach(
                it -> addExcerciseAndGenerateIndex(it, risposteMap));

        boolean allKeysAremMatched = true;
        for (String domKey : domandeMap.keySet()) {
            allKeysAremMatched &= checkKeyMap(domKey, risposteMap);
        }

        if (!allKeysAremMatched) {
            throw new RuntimeException("Mancano delle chiavi all'interno degli esercizi ");
        }

        for (String domKey : domandeMap.keySet()) {
            deck.addCard(
            		LeftFormatDecorator.decorateWithLeftFormat(
            				new AnkiCard(createFrontContent(domandeMap.get(domKey)),
            							 createBackContent(risposteMap.get(domKey)))
            ));
        }

    }
    
    
    private Element createFrontContent(String content) {
    	Element e = new Element(Tag.valueOf("p"), "");
    	e.append(content.replace("\n", "<br>"));
    	
    	return e;
    }
    
    private Element createBackContent(String content) {
    	return new Element(Tag.valueOf("p"), "").appendText(content);
    }
    
    void addExcerciseAndGenerateIndex(String str, Map<String, String> map) {
    	Matcher matcher = SINGLE_EX_PATTERN_WITH_UNDERSCORE.matcher(str);
    	
    	String newKey = "";
        while (matcher.find()) {
            String key = matcher.group(0);
            
            if (key.contentEquals("1_")) {
            	generatedIndex++;
            }
            newKey = generatedIndex+"."+key;
            map.put(newKey, str.replaceAll(key, ""));
        }
    }


    void addParsedCard(AnkiDeck deck, String input, LanguageLearningAnkiCard.PracticeMakesPerfectEnum cardKind) {

        String qa[] = parseDomandeRisposte(input);

        Map<String, String> domandeMap = new HashMap<>();
        Stream.of(splitString(qa[DOMANDE_INDEX], IParser.PIPE_SEPARATOR)).forEach(
                it -> addExcercise(it, domandeMap));

        Map<String, String> risposteMap = new HashMap<>();
        Stream.of(splitString(qa[RISPOSTE_INDEX], IParser.PIPE_SEPARATOR)).forEach(
                it -> addExcercise(it, risposteMap));

        boolean allKeysAremMatched = true;
        for (String domKey : domandeMap.keySet()) {
            allKeysAremMatched &= checkKeyMap(domKey, domandeMap);
        }

        if (!allKeysAremMatched) {
            throw new RuntimeException("Mancano delle chiavi all'interno degli esercizi ");
        }

        for (String domKey : domandeMap.keySet()) {
            deck.addCard(new LanguageLearningAnkiCard(
                    domandeMap.get(domKey),
                    risposteMap.get(domKey),
                    cardKind
            ));
        }

    }


    void addNestedParsedCard(AnkiDeck deck, String input, LanguageLearningAnkiCard.PracticeMakesPerfectEnum cardKind) {

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
                deck.addCard(card);
            }
        }

    }


    void addNestedExcercise(String str, Map<String, Map<String, String>> map) {
        addToNestedMapsWithRegexChecking(str, map, NUM_EX_PATTERN, SINGLE_EX_PATTERN);
    }

    void addExcercise(String str, Map<String, String> map) {
        addToMapWithRegexChecking(str, map, NUM_EX_PATTERN);
    }
    
   

    Map<String, String> addToMapWithRegexChecking(String str, Map<String, String> map, Pattern regex) {
        Matcher matcher = regex.matcher(str);
        while (matcher.find()) {
            String key = matcher.group(0);
            map.put(key, str.replaceAll(key, ""));
        }
        return map;
    }

    void addToNestedMapsWithRegexChecking(String str, Map<String, Map<String, String>> map, Pattern outerRegex, Pattern innerRegex) {
        Matcher matcher = outerRegex.matcher(str);
        while (matcher.find()) {
            Map<String, String> innerMap = new HashMap<>();
            String key = matcher.group(0);
            String value = str.replaceAll(key, "");

            Matcher innerMatcher = innerRegex.matcher(value);
            List<String> numEx = new ArrayList<>();
            while (innerMatcher.find())
                numEx.add(innerMatcher.group(0));

            for (int i = 0; i < numEx.size() - 1; i++) {
                String innerValue = value.substring(value.indexOf(numEx.get(i)), value.indexOf(numEx.get(i + 1)));
                innerMap.put(numEx.get(i), innerValue.replaceAll(numEx.get(i), ""));
            }
            String lastKey = numEx.get(numEx.size() - 1);
            int indexOf = value.indexOf(numEx.get(numEx.size() - 1));
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
        if (qa.length != 2)
            throw new RuntimeException("File non parsato correttamente. Manca il campo: " + QA_SEPARATOR);
        return qa;
    }

    // TODO - verificare perch� lancia l'errore per lo splitter "\\|"
    private String[] splitString(String str, String splitter) {
        //		if (!str.contains(splitter))
        //			throw new RuntimeException("Lo splitter: " + splitter + " non � presente nella stringa: " + str);
        return str.split(splitter);
    }


}