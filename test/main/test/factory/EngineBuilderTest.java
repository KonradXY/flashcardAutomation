package main.test.factory;

import static main.java.utils.Property.EVERNOTE_DIR;
import static main.java.utils.Property.GENERIC_DIR;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.KINDLE_DIR;
import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.factory.AbstractAnkiEngine;
import main.java.factory.EngineBuilder;
import main.java.model.evernoteModel.EvernoteHtmlParser;
import main.java.model.kindleModel.KindleClippingPrinter;
import main.java.model.kindleModel.KindleClippingsParser;
import main.java.model.languageLearningModel.LanguageLearningParser;
import main.java.model.languageLearningModel.LanguageLearningPrinter;
import main.java.model.simplemodel.SimpleParser;
import main.java.model.simplemodel.SimplePrinter;
import main.java.model.simplemodel.SimpleReader;

class EngineBuilderTest {

	private EngineBuilder builder = new EngineBuilder();
	private AbstractAnkiEngine ankiEngine;
	private String[] args;
	
	@Test
	void testDefaultEngineModel() {
		args = new String[]{""};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), SimpleReader.class);
		assertEquals(ankiEngine.getParser().getClass(), SimpleParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), SimplePrinter.class);
		
		assertEquals(ankiEngine.getInputContent(), INPUT_DIR + GENERIC_DIR );
		assertEquals(ankiEngine.getOutputContent(), OUTPUT_DIR + GENERIC_DIR);
	}
	
	@Test
	void testEvernoteEngineModel() {
		args = new String[]{"evernote"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), SimpleReader.class);
		assertEquals(ankiEngine.getParser().getClass(), EvernoteHtmlParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), SimplePrinter.class);
		
		assertEquals(ankiEngine.getInputContent(), INPUT_DIR + EVERNOTE_DIR);
		assertEquals(ankiEngine.getOutputContent(), OUTPUT_DIR + EVERNOTE_DIR + "evernoteParsed.txt");	// TODO <-- questo e' da eliminare !
	}
	
	@Test
	void testKindleEngineModel() {
		args = new String[]{"kindle"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), SimpleReader.class);
		assertEquals(ankiEngine.getParser().getClass(), KindleClippingsParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), KindleClippingPrinter.class);
		
		assertEquals(ankiEngine.getInputContent(), INPUT_DIR + KINDLE_DIR);
		assertEquals(ankiEngine.getOutputContent(), OUTPUT_DIR + KINDLE_DIR);
	}
	
	@Test
	void testLanguageLearningEngineModel() {
		args = new String[]{"languageLearning"};
		ankiEngine = builder.createTextEngine(args);
		
		assertEquals(ankiEngine.getReader().getClass(), SimpleReader.class);
		assertEquals(ankiEngine.getParser().getClass(), LanguageLearningParser.class);
		assertEquals(ankiEngine.getPrinter().getClass(), LanguageLearningPrinter.class);
		
		assertEquals(ankiEngine.getInputContent(), INPUT_DIR + LANGUAGE_LEARNING_DIR);
		assertEquals(ankiEngine.getOutputContent(), OUTPUT_DIR + LANGUAGE_LEARNING_DIR);
	}

}
