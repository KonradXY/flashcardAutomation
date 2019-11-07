package main.java.utils;

import static main.java.launcher.Launcher.inputContent;
import static main.java.launcher.Launcher.outputContent;
import static main.java.launcher.Launcher.parser;
import static main.java.launcher.Launcher.printer;
import static main.java.launcher.Launcher.reader;
import static main.java.utils.Property.EVERNOTE_DIR;
import static main.java.utils.Property.GENERIC_DIR;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.KINDLE_DIR;
import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

import org.apache.log4j.Logger;

import main.java.abstractModel.AbstractReader;
import main.java.baseModel.SimpleParser;
import main.java.baseModel.SimplePrinter;
import main.java.evernoteModel.EvernoteHtmlParser;
import main.java.kindleModel.KindleClippingPrinter;
import main.java.kindleModel.KindleClippingsParser;
import main.java.languageLearningModel.LanguageLearningParser;
import main.java.languageLearningModel.LanguageLearningPrinter;
import main.java.strategy.ReadingStrategy;

public class FlashcardEngineFactory {

	public static final Logger log = Logger.getLogger(FlashcardEngineFactory.class);

	public static void buildFlashcardEngine(String[] inputParam) {

		if (inputParam.length > 1)
			throw new RuntimeException("Error: Usage launcher.main [inputParam]");
		
		inputContent = INPUT_DIR;
		outputContent = OUTPUT_DIR;

		String input = inputParam[0];
		switch(input) {
			case "evernote": buildEvernote(); break;
			case "kindle": buildKindle(); break;
			case "languageLearning": buildLanguageLearning(); break;
			default : buildDefault(); break;
		}
		log.info("Cartella di input: " + inputContent);
		log.info("Cartella di output: " + outputContent);
	}

	private static void buildDefault() {
		log.info("called default engine builder");
		buildDefaultEngine();
		buildDefaultProps();
	}
	private static void buildKindle() {
		log.info("called kindle engine builder");
		buildKindleEngine();
		buildKindleProps();
	}
	private static void buildLanguageLearning() {
		log.info("called language learning engine builder");
		buildLanguageLearningEngine();
		buildLanguageLearningProps();
	}
	private static void buildEvernote() {
		log.info("called evernote engine builder");
		buildEvernoteEngine();
		buildEvernoteProps();
	}

	private static void buildDefaultEngine() {
		reader = new AbstractReader(ReadingStrategy.GENERAL);
		parser = new SimpleParser();
		printer = new SimplePrinter();
	}
	private static void buildEvernoteEngine() {
		reader = new AbstractReader(ReadingStrategy.EVERNOTE);
		parser = new EvernoteHtmlParser();
		printer = new SimplePrinter();
	}
	private static void buildKindleEngine() {
		reader = new AbstractReader(ReadingStrategy.KINDLE);
		parser = new KindleClippingsParser();
		printer = new KindleClippingPrinter();
	}
	private static void buildLanguageLearningEngine() {
		reader = new AbstractReader(ReadingStrategy.LANGUAGE_LEARNING);
		parser = new LanguageLearningParser();
		printer = new LanguageLearningPrinter();
	}

	private static void buildEvernoteProps() {
		inputContent  += EVERNOTE_DIR;
		outputContent += EVERNOTE_DIR + "evernoteParsed.txt";
	}

	private static void buildKindleProps() {
		inputContent  += KINDLE_DIR;
		outputContent += KINDLE_DIR;
	}

	private static void buildLanguageLearningProps() {
		inputContent 	+= LANGUAGE_LEARNING_DIR;
		outputContent  += LANGUAGE_LEARNING_DIR;
	}

	private static void buildDefaultProps() {
		inputContent 	+= GENERIC_DIR;
		outputContent  += GENERIC_DIR;
	}

}
