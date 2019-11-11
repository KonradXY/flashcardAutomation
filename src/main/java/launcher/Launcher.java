package main.java.launcher;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static main.java.utils.Property.WEB_CRAWLER_DIR;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.java.baseModel.SimpleReader;
import main.java.config.SpringConfig;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.facade.FlashcardFacade;

public class Launcher {

	private final static Logger log = Logger.getLogger(Launcher.class);
	
	public static String inputContent;
	public static String outputContent;

	public static SimpleReader reader;
	public static IParser parser;
	public static IPrinter printer;

	public static void main(String[] args) {
		
		long timeSpent = System.currentTimeMillis();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		log.info("... Inizio creazione flashcard ...");

		if (args != null) {
			String inputArg = args[0].toLowerCase();
			if (inputArg.contains("webcrawling")) {
				log.info(" ====>>> launching webcrawling mode");
				String inputFile  	= INPUT_DIR + WEB_CRAWLER_DIR + "2000_parole_lista_2.txt";
				String outputFile 	= OUTPUT_DIR+ WEB_CRAWLER_DIR + "scrapedList.txt";
				buildFlashCardsFromWeb(ctx, inputFile, outputFile);
			}
			else if (inputArg.contains("clozecrawling")) {
				// TODO - sta roba va a finire all'interno del build properties (che finisce all'interno del main praticamente -> in questo caso il mediator si pone all'interno del parsing ma dovrei studiarmela meglio come cosa
				log.info(" ====>>> launching clozeCrawling mode");
				String inputFile  	= INPUT_DIR + WEB_CRAWLER_DIR + "2000_parole_lista_2.txt";
				String outputFile 	= OUTPUT_DIR+ WEB_CRAWLER_DIR + "scrapedList.txt";
				buildClozeFlashcardsFromWeb(ctx, inputFile, outputFile);
			}
		else {
				buildFlashcardsFromFile(args);
			}

		}


		log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis()-timeSpent)/1000 + " sec");

	}
	
	private static void buildFlashcardsFromFile(String[] args) {
		try {
			Path input = Paths.get(inputContent);
			Path output = Paths.get(outputContent);
			FlashcardFacade.buildFlashcardsFromTextFile(input, output, args);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void buildFlashCardsFromWeb(ApplicationContext ctx, String inputFile, String outputFile) {
		try {
			FlashcardFacade.buildFlashcardsFromWeb(ctx, inputFile, outputFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void buildClozeFlashcardsFromWeb(ApplicationContext ctx, String input, String output) {
		try {
			FlashcardFacade.buildClozeFlashcardsFromWeb(ctx, input, output);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	

	
	

	





}
