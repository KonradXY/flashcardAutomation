package main.java.engines.factories;

import main.java.engines.WebCrawlerEngine;
import main.java.engines.webengines.SpanishClozeEngine;
import main.java.engines.webengines.SpanishDefinitionEngine;
import main.java.engines.webengines.SpanishGeneralEngine;
import org.apache.log4j.Logger;

import java.util.List;


public abstract class WebEngineFactory extends AbstractEngineFactory {

    private static final Logger log = Logger.getLogger(WebEngineFactory.class);

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
        }

        webEngine.buildEngine();

        return webEngine;
    }
}
