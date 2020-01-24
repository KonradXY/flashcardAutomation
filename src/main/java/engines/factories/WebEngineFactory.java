package main.java.engines.factories;

import main.java.engines.TextEngine;
import main.java.engines.WebCrawlerEngine;
import org.apache.log4j.Logger;

import java.util.List;

public class WebEngineFactory extends AbstractEngineFactory {

    private static final Logger log = Logger.getLogger(WebEngineFactory.class);

    @Override
    protected WebCrawlerEngine createEngine(List<String> inputParam) {
        WebCrawlerEngine webEngine = null;

        if (inputParam.contains("general")) {
            log.info(" ====>>> launching webcrawling mode");


        } else if (inputParam.contains("simple")) {
            log.info(" ====>>> launching simpleDefinitions mode");


        } else if (inputParam.contains("cloze")) {
            log.info(" ====>>> launching clozeCrawling mode");

        }

        webEngine.buildEngine();

        return webEngine;
    }
}
