package main.java.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WebCrawlerEngineUtilsTest {

    static StopwordsEngine webCrawlerUtils;

    private static final int SPANISH_STOPWORDS_SIZE_LIST = 732;

    @BeforeAll
    public static void setup() {
        webCrawlerUtils = new StopwordsEngine();
    }

    @Test
    void checkSpanishStopwordsProperlyLoaded() {
        Set<String> spanishStopwords = webCrawlerUtils.getSpanishStopwords();
        assertTrue(spanishStopwords != null);
        assertEquals(SPANISH_STOPWORDS_SIZE_LIST, spanishStopwords.size());
    }

    @Test
    void checkStopwordsAreRemovedProperly() {
        Map<Path, List<String>> contentMapExample = new HashMap<>();
        List<String> contentExample = Arrays.asList("a", "parola corretta", "m", "seconda parola corretta");
        contentMapExample.put(Paths.get("./"), contentExample);

        //when
        webCrawlerUtils.checkForStopWords(contentMapExample, webCrawlerUtils.getSpanishStopwords());

        // then
        assertEquals(Arrays.asList("parola corretta", "seconda parola corretta"), contentMapExample.entrySet().iterator().next());

    }

}