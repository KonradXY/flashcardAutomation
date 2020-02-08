package main.java.engines.webengines;

import main.java.engines.WebCrawlerEngine;
import main.java.model.readers.TextListFileReader;
import main.java.model.webcrawler.SpanishClozeWebCrawler;
import main.java.modelDecorator.WebParsedClozedCardDecorator;
import main.java.utils.ClozeEngine;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;

public class SpanishClozeEngine extends WebCrawlerEngine {

    @Override
    public void buildEngine() {
        reader = new TextListFileReader();
        webCrawler = new SpanishClozeWebCrawler(new ClozeEngine(), new WordReferenceDefinitionPage(), new WebParsedClozedCardDecorator());  // TODO <--- da sistemare con guice
    }
}
