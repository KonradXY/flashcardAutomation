package main.java.facade;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import main.java.engines.WebCrawlerEngine;
import main.java.engines.factories.WebCrawlerEngineFactory;
import main.java.netutilities.CertificateManager;

public class WebCrawlerFacade {

    private static final Logger log = Logger.getLogger(WebCrawlerFacade.class);

    private final WebCrawlerEngineFactory engineFactory;

    @Inject
    WebCrawlerFacade(WebCrawlerEngineFactory engineFactory) {
        this.engineFactory = engineFactory;
    }

    public void createFlashcardsScrapingTheWeb(List<String> args) {

        try {
            CertificateManager.doTrustToCertificates();

            WebCrawlerEngine webCrawlerEngine = engineFactory.createEngine(args);

            webCrawlerEngine.createFlashcards();

        } catch (Exception ex) {
            log.error("Errore nella creazione di flashcard tramite webcrawler: " + ex);
            throw new RuntimeException(ex);
        }

    }

}
