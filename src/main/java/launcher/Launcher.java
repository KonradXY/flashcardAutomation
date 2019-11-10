package main.java.launcher;

import static main.java.utils.FlashcardEngineFactory.buildFlashcardEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.baseModel.AbstractAnkiCard;
import main.java.baseModel.SimpleReader;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;

public class Launcher {

	private final static Logger log = Logger.getLogger(Launcher.class);
	
	public static String inputContent;
	public static String outputContent;

	public static SimpleReader reader;
	public static IParser parser;
	public static IPrinter printer;

	public static void main(String[] args) {

		try {

			long timeSpent = System.currentTimeMillis();

			buildFlashcardEngine(args);

			buildFlashcards();
			
			log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis()-timeSpent)/1000 + " sec");
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static void buildFlashcards() throws IOException {
		Map<Path, String> input = reader.readFile(Paths.get(inputContent));
		List<AbstractAnkiCard> parsedAnkiCards = parser.parseToAnkiFlashcard(input);
		printer.printFile(outputContent, parsedAnkiCards);
	}
	

	
	

	





}
