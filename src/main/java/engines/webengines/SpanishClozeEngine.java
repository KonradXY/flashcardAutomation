package main.java.engines.webengines;

import main.java.engines.WebCrawlerEngine;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.webcrawler.SpanishClozeWebCrawler;
import main.java.modelDecorator.WebParsedClozedCardDecorator;
import main.java.strategy.ReadingFormatStrategy;
import main.java.utils.ClozeEngine;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;

public class SpanishClozeEngine extends WebCrawlerEngine {

    @Override
    public void buildEngine() {
        webCrawler = new SpanishClozeWebCrawler(new ClozeEngine(), new WordReferenceDefinitionPage(), new WebParsedClozedCardDecorator());  // TODO <--- da sistemare con guice
    }
}
