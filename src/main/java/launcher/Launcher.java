package main.java.launcher;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static main.java.utils.Property.WEB_CRAWLER_DIR;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.java.config.AnkiApplicationModule;
import main.java.facade.FlashcardFacade;

public class Launcher {

	private final static Logger log = Logger.getLogger(Launcher.class);
	
	public static void main(String[] args) {

		long timeSpent = System.currentTimeMillis();
		log.info("... Inizio creazione flashcard ...");

		Injector injector = Guice.createInjector(new AnkiApplicationModule());
		FlashcardFacade facade = injector.getInstance(FlashcardFacade.class);

		if (args == null) throw new RuntimeException("Errore necessari dei parametri ! (Da sistemare !)");
		
		String inputArg = args[0].toLowerCase();
		
		// TODO - tutto sto pezzo deve andare all'interno dell'engineBuilder
		if (inputArg.contains("webcrawling")) {
			log.info(" ====>>> launching webcrawling mode");
			String inputDir  	= WEB_CRAWLER_DIR + "1k_lista2.txt";
			String outputDir 	= WEB_CRAWLER_DIR + "scrapedList.txt";
			buildFlashCardsFromWeb(inputDir, outputDir, facade);
		}
		else if (inputArg.contains("clozecrawling")) {
			// TODO - sta roba va a finire all'interno del build properties (che finisce all'interno del main praticamente -> in questo caso il mediator si pone all'interno del parsing ma dovrei studiarmela meglio come cosa
			log.info(" ====>>> launching clozeCrawling mode");
			String inputDir  	= WEB_CRAWLER_DIR + "1k_lista2.txt";
			String outputDir 	= WEB_CRAWLER_DIR + "scrapedList.txt";
			buildClozeFlashcardsFromWeb(inputDir, outputDir, facade);
		}

		else {
			log.info(" ====>>> launching parsing from file ");
			buildFlashcardsFromFile(args, facade);
		}

		log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis()-timeSpent)/1000 + " sec");

	}

	private static void buildFlashcardsFromFile(String[] args, FlashcardFacade facade) {
		try {
			facade.buildFlashcardsFromTextFile(args);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void buildFlashCardsFromWeb(String inputFile, String outputFile, FlashcardFacade facade) {
		try {
			facade.buildFlashcardsFromWeb(inputFile, outputFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void buildClozeFlashcardsFromWeb(String input, String output, FlashcardFacade facade) {
		try {
			facade.buildClozeFlashcardsFromWeb(input, output);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}













}
