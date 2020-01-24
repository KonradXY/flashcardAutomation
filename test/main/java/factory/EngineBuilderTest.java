package main.java.factory;

import static main.java.utils.Property.EVERNOTE_PATH;
import static main.java.utils.Property.GENERIC_PATH;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.KINDLE_PATH;
import static main.java.utils.Property.LANGUAGE_LEARNING_PATH;
import static main.java.utils.Property.OUTPUT_DIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.engines.TextEngine;
import main.java.engines.factories.TextEngineFactory;
import main.java.model.DefaultParser;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.evernote.EvernoteHtmlParser;
import main.java.model.kindle.KindleClippingPrinter;
import main.java.model.kindle.KindleClippingsParser;
import main.java.model.languageLearning.LanguageLearningParser;

import java.util.Arrays;
import java.util.List;

class EngineBuilderTest {

	private TextEngineFactory builder = new TextEngineFactory();
	private TextEngine ankiEngine;
	private List<String> args;
	
	@Test
	void testDefaultEngineModel() {
		args = Arrays.asList("");
		ankiEngine = (TextEngine)builder.createEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), DefaultParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), TextFilePrinter.class);
		
		assertEquals(ankiEngine.getFullInputDir(), INPUT_DIR + GENERIC_PATH);
		assertEquals(ankiEngine.getFullOutputDir(), OUTPUT_DIR + GENERIC_PATH);
	}
	
	@Test
	void testEvernoteEngineModel() {
		args = Arrays.asList("evernote");
		ankiEngine = (TextEngine)builder.createEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), EvernoteHtmlParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), TextFilePrinter.class);
		
		assertEquals(ankiEngine.getFullInputDir(), INPUT_DIR + EVERNOTE_PATH);
		assertEquals(ankiEngine.getFullOutputDir(), OUTPUT_DIR + EVERNOTE_PATH);
	}
	
	@Test
	void testKindleEngineModel() {
		args = Arrays.asList("kindle");
		ankiEngine = (TextEngine)builder.createEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), KindleClippingsParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), KindleClippingPrinter.class);
		
		assertEquals(ankiEngine.getFullInputDir(), INPUT_DIR + KINDLE_PATH);
		assertEquals(ankiEngine.getFullOutputDir(), OUTPUT_DIR + KINDLE_PATH);
	}
	
	@Test
	void testLanguageLearningEngineModel() {
		args = Arrays.asList("languageLearning");
		ankiEngine = (TextEngine)builder.createEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), TextFileReader.class);
		assertEquals(ankiEngine.getParser().getClass(), LanguageLearningParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), TextFilePrinter.class);
		
		assertEquals(ankiEngine.getFullInputDir(), INPUT_DIR + LANGUAGE_LEARNING_PATH);
		assertEquals(ankiEngine.getFullOutputDir(), OUTPUT_DIR + LANGUAGE_LEARNING_PATH);
	}

}
