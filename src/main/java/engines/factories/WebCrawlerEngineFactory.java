package main.java.engines.factories;

import com.google.inject.Singleton;
import main.java.engines.WebCrawlerEngine;
import main.java.engines.webengines.SpanishClozeEngine;
import main.java.engines.webengines.SpanishDefinitionEngine;
import main.java.engines.webengines.SpanishGeneralEngine;
import main.java.utils.Property;
import org.apache.log4j.Logger;

import java.util.List;

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
        }

        webEngine.setParserInputDir(Property.WEB_CRAWLER_PATH);
        webEngine.setParserOutputDir(Property.WEB_CRAWLER_PATH);
        webEngine.buildEngine();

        return webEngine;
    }
}
