package main.java.engines.factories;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static main.java.utils.Property.WEB_CRAWLER_PATH;

import java.security.InvalidParameterException;
import java.util.List;

import main.java.engines.webengines.LeetCodeEngine;
import org.apache.log4j.Logger;

import com.google.inject.Singleton;

import main.java.engines.WebCrawlerEngine;
import main.java.engines.webengines.SpanishClozeEngine;
import main.java.engines.webengines.SpanishDefinitionEngine;
import main.java.engines.webengines.SpanishGeneralEngine;

@Singleton
public class WebCrawlerEngineFactory extends AbstractEngineFactory {

    private static final Logger log = Logger.getLogger(WebCrawlerEngineFactory.class);

    @Override
    public WebCrawlerEngine createEngine(List<String> inputParam) {
        WebCrawlerEngine webEngine = null;

        if (inputParam.contains("general")) {
            log.info(" ====>>> launching webcrawling mode");
            webEngine = new SpanishGeneralEngine();

        } else if (inputParam.contains("simple")) {
            log.info(" ====>>> launching simpleDefinitions mode");
            webEngine = new SpanishDefinitionEngine();

        } else if (inputParam.contains("cloze")) {
            log.info(" ====>>> launching clozeCrawling mode");
            webEngine = new SpanishClozeEngine();
        } else if (inputParam.contains("leetCode")) {
            log.info(" ====>>> launching LeetCode Web Parsing");
            webEngine = new LeetCodeEngine();
        }
        else {
            throw new InvalidParameterException("The webengine called is not supported or malformed: " + inputParam);
        }

        webEngine.setInputDir(INPUT_DIR + WEB_CRAWLER_PATH);
        webEngine.setOutputDir(OUTPUT_DIR + WEB_CRAWLER_PATH);  // TODO - sti parametri dovrebbero essere settati dal costruttore !
        webEngine.buildEngine();

        return webEngine;
    }
}
