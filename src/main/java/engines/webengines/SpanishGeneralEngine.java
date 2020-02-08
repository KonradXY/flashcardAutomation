package main.java.engines.webengines;

import com.google.inject.Singleton;
import main.java.engines.WebCrawlerEngine;
import main.java.model.readers.TextListFileReader;
import main.java.model.webcrawler.SpanishGeneralWebCrawler;
import main.java.webscraper.reverso.ReversoSpanishScraper;
import main.java.webscraper.wordreference.WordReferenceDefinitionPage;
import main.java.webscraper.wordreference.WordReferenceSynonimsPage;


@Singleton
public class SpanishGeneralEngine extends WebCrawlerEngine {

    @Override
    public void buildEngine() {
        reader = new TextListFileReader();
        webCrawler = new SpanishGeneralWebCrawler(new ReversoSpanishScraper(), new WordReferenceDefinitionPage(), new WordReferenceSynonimsPage());
    }
}
