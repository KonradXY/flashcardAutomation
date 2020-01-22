package main.java.utils;

import com.google.inject.Singleton;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.nio.CharBuffer;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Singleton
public class ClozeEngine {

    public Map<String, String> createClozeMap(Map<String, String> map, String word) {
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
}
