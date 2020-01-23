package main.java.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.service.SpanishWebCrawlerService;
import main.java.netutilities.CertificateManager;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static main.java.utils.Property.*;
import static main.java.utils.Property.WEB_CRAWLER_DIR;

@Singleton
public class WebCrawlerFacade {

    private static final Logger log = Logger.getLogger(WebCrawlerFacade.class);

    private final SpanishWebCrawlerService spanishWebCrawlerService;

    @Inject
    WebCrawlerFacade(SpanishWebCrawlerService spanishWebCrawlerService) {
        this.spanishWebCrawlerService = spanishWebCrawlerService;
    }

    public void createFlashcardsScrapingTheWeb(List<String> args) {
        try {

            CertificateManager.doTrustToCertificates();

            if (args.contains("general")) {
                log.info(" ====>>> launching webcrawling mode");
                String inputDir = INPUT_DIR + WEB_CRAWLER_DIR + "1k_lista2.txt";
                String outputDir = OUTPUT_DIR + WEB_CRAWLER_DIR + "scrapedList.txt";
                spanishWebCrawlerService.createGeneralFlashcards(inputDir, outputDir);

            } else if (args.contains("simple")) {
                log.info(" ====>>> launching simpleDefinitions mode");
                String inputDir = INPUT_DIR + WEB_CRAWLER_DIR + "vocabolario b1 animali.txt";
                String outputDir = OUTPUT_DIR + WEB_CRAWLER_DIR + "simpleDefinitions.txt";
                spanishWebCrawlerService.createDefinitionFlashcards(inputDir, outputDir);

            } else if (args.contains("cloze")) {
                log.info(" ====>>> launching clozeCrawling mode");
                String inputDir = INPUT_DIR + WEB_CRAWLER_DIR + "1k_lista2.txt";
                String outputDir = OUTPUT_DIR + WEB_CRAWLER_DIR + "scrapedList.txt";
                spanishWebCrawlerService.createClozeFlashcards(inputDir, outputDir);

            }
        } catch (Exception ex) {
            log.error("Errore nella creazione di flashcard tramite webcrawler: " + ex);
            throw new RuntimeException(ex);
        }

    }


    public void metodoCheWrappaLaRobaFuoriDalSpanishMediator(String inputFile, String outputFile) {

        // TODO - la roba deve funzionare in questa maniera:
		/*
			1. Prendere in input le parole da aprsare (questo viene fatto in una botta sola)

			2. Per ogni parola
				2.1 Effettuare lo scraping e la creazione degli oggetti AnkiCard
				2.2 Nel caso decorare
				2.3 Scrivere su file	// TODO - la scrittura sarebbe utile farla asincrona

		 */

        // TODO - dovrei capire dove piazzarlo. In piu' vorrei modellare sta roba in stile TextEngine (stile metodo createFlashCard praticamente)

        int numWords = 0;

        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);	// TODO <--- vedere meglio la gestione
        }
    }
}
