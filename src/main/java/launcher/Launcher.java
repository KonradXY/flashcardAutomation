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
		log.info("... Inizio creazione flashcard ...");

		if (args != null && args[0].contains("webcrawler")) {
			String inputFile  	= INPUT_DIR + WEB_CRAWLER_DIR + "2000_parole_lista_2.txt";
			String outputFile 	= OUTPUT_DIR+ WEB_CRAWLER_DIR + "scrapedList.txt";
			buildFlashCardsFromWeb(inputFile, outputFile);
		}
		else { 
			buildFlashcardsFromFile(args);
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
	
	private static void buildFlashCardsFromWeb(String inputFile, String outputFile) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		try {
			FlashcardFacade.buildFlashcardsFromWeb(ctx, inputFile, outputFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	

	
	

	





}
