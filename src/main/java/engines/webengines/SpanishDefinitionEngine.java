package main.java.engines.webengines;

import com.google.inject.Singleton;
import main.java.engines.WebCrawlerEngine;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.webcrawler.SpanishDefinitionWebCrawler;
import main.java.strategy.ReadingFormatStrategy;
import main.java.webscraper.wordreference.WordReferenceTranslationPage;

@Singleton
public class SpanishDefinitionEngine extends WebCrawlerEngine  {

    @Override
    public void buildEngine() {
        webCrawler = new SpanishDefinitionWebCrawler(new WordReferenceTranslationPage());
    }
}
