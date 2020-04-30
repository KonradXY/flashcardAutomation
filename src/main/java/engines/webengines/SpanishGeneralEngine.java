package main.java.engines.webengines;

import com.google.inject.Singleton;

import main.java.engines.WebCrawlerEngine;
import main.java.model.readers.TextListFileReader;
import main.java.model.webcrawler.SpanishGeneralWebCrawler;
import main.java.webpages.reverso.ReversoDefinitionPage;
import main.java.webpages.wordreference.WordReferenceDefinitionPage;
import main.java.webpages.wordreference.WordReferenceSynonimsPage;


@Singleton
public class SpanishGeneralEngine extends WebCrawlerEngine {

    @Override
    public void buildEngine() {
        reader = new TextListFileReader();
        webCrawler = new SpanishGeneralWebCrawler(new ReversoDefinitionPage(), new WordReferenceDefinitionPage(), new WordReferenceSynonimsPage());
    }
}
