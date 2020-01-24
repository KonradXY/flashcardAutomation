package main.java.engines.webengines;

import com.google.inject.Singleton;
import main.java.engines.WebCrawlerEngine;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.webcrawler.SpanishDefinitionWebCrawler;
import main.java.model.webcrawler.SpanishGeneralWebCrawler;
import main.java.strategy.ReadingFormatStrategy;
import main.java.webscraper.reverso.ReversoSpanishScraper;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;
import main.java.webscraper.wordreference.WordReferenceSynonimsPage;
import main.java.webscraper.wordreference.WordReferenceTranslationPage;


@Singleton
public class SpanishGeneralEngine extends WebCrawlerEngine {

    @Override
    public void buildEngine() {
        reader = new TextFileReader(ReadingFormatStrategy.ADD_PIPE);
        webCrawler = new SpanishGeneralWebCrawler(new ReversoSpanishScraper(), new WordReferenceDefinitionPage(), new WordReferenceSynonimsPage());
        printer = new TextFilePrinter();
    }
}
