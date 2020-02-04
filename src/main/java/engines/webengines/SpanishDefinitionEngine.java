package main.java.engines.webengines;

import com.google.inject.Singleton;
import main.java.engines.WebCrawlerEngine;
import main.java.model.readers.TextListFileReader;
import main.java.model.webcrawler.SpanishDefinitionWebCrawler;
import main.java.webscraper.wordreference.WordReferenceTranslationPage;

@Singleton
public class SpanishDefinitionEngine extends WebCrawlerEngine  {

    @Override
    public void buildEngine() {
        reader = new TextListFileReader();
        webCrawler = new SpanishDefinitionWebCrawler(new WordReferenceTranslationPage());
    }
}
