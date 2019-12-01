package main.java.factory;

import static main.java.utils.Property.EVERNOTE_DIR;
import static main.java.utils.Property.GENERIC_DIR;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.KINDLE_DIR;
import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import main.java.model.SimpleParser;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.evernote.EvernoteHtmlParser;
import main.java.model.kindle.KindleClippingPrinter;
import main.java.model.kindle.KindleClippingsParser;
import main.java.model.languageLearning.LanguageLearningParser;
import main.java.model.languageLearning.LanguageLearningPrinter;

@RunWith(JUnitPlatform.class)
class EngineBuilderTest {

	private EngineBuilder builder = new EngineBuilder();
	private AbstractAnkiEngine ankiEngine;
	private String[] args;
	
	@Test
	void testDefaultEngineModel() {
		args = new String[]{""};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), SimpleParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), TextFilePrinter.class);
		
		assertEquals(ankiEngine.getInputDestination(), INPUT_DIR + GENERIC_DIR );
		assertEquals(ankiEngine.getOutputDestination(), OUTPUT_DIR + GENERIC_DIR);
	}
	
	@Test
	void testEvernoteEngineModel() {
		args = new String[]{"evernote"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), EvernoteHtmlParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), TextFilePrinter.class);
		
		assertEquals(ankiEngine.getInputDestination(), INPUT_DIR + EVERNOTE_DIR);
		assertEquals(ankiEngine.getOutputDestination(), OUTPUT_DIR + EVERNOTE_DIR + "evernoteParsed.txt");	// TODO <-- questo e' da eliminare !
	}
	
	@Test
	void testKindleEngineModel() {
		args = new String[]{"kindle"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), KindleClippingsParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), KindleClippingPrinter.class);
		
		assertEquals(ankiEngine.getInputDestination(), INPUT_DIR + KINDLE_DIR);
		assertEquals(ankiEngine.getOutputDestination(), OUTPUT_DIR + KINDLE_DIR);
	}
	
	@Test
	void testLanguageLearningEngineModel() {
		args = new String[]{"languageLearning"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), LanguageLearningParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), LanguageLearningPrinter.class);
		
		assertEquals(ankiEngine.getInputDestination(), INPUT_DIR + LANGUAGE_LEARNING_DIR);
		assertEquals(ankiEngine.getOutputDestination(), OUTPUT_DIR + LANGUAGE_LEARNING_DIR);
	}

}
