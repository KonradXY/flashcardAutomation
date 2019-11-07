package main.java.launcher;

import static main.java.utils.FlashcardEngineFactory.buildFlashcardEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.abstractModel.AbstractReader;
import main.java.abstractModel.IParser;
import main.java.abstractModel.IPrinter;
import main.java.utils.Property;

public class Launcher {

	private final static Logger logger = Logger.getLogger(Launcher.class);
	
	public static String inputContent;
	public static String outputContent;

	public static AbstractReader reader;
	public static IParser parser;
	public static IPrinter printer;

	public static void main(String[] args) {

		try {

			long timeSpent = System.currentTimeMillis();

			buildFlashcardEngine(args);

			buildFlashcards();
			
			logger.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis()-timeSpent)/1000 + " sec");
			
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
