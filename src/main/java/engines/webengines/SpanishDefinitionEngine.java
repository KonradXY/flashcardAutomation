package main.java.engines.webengines;

import com.google.inject.Singleton;

import main.java.engines.WebCrawlerEngine;
import main.java.model.readers.TextListFileReader;
import main.java.model.webcrawler.SpanishSimpleDefinitionWebCrawler;
import main.java.webpages.wordreference.WordReferenceDefinitionPage;
import main.java.webpages.wordreference.WordReferenceTranslationPage;

@Singleton
public class SpanishDefinitionEngine extends WebCrawlerEngine  {

	// TODO - sta roba dovrebbe essere fatta con guice
	
    @Override
    public void buildEngine() {
        reader = new TextListFileReader();
        webCrawler = new SpanishSimpleDefinitionWebCrawler(new WordReferenceTranslationPage(), new WordReferenceDefinitionPage());
    }
}
