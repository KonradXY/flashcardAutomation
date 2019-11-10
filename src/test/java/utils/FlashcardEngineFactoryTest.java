package test.java.utils;

import static main.java.launcher.Launcher.parser;
import static main.java.launcher.Launcher.printer;
import static main.java.launcher.Launcher.reader;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.java.baseModel.SimpleReader;
import main.java.kindleModel.KindleClippingPrinter;
import main.java.kindleModel.KindleClippingsParser;
import main.java.utils.FlashcardEngineFactory;

public class FlashcardEngineFactoryTest {

	@Test
	public void testKindleEngineFactory() {
		FlashcardEngineFactory.buildFlashcardEngine(new String[]{"kindle"});
		assertTrue(reader.getClass().isAssignableFrom(SimpleReader.class));
		assertTrue(parser.getClass().isAssignableFrom(KindleClippingsParser.class));
		assertTrue(printer.getClass().isAssignableFrom(KindleClippingPrinter.class));
	}
	
	@Test
	public void testEvernoteEngineFactoy() {
		fail("not implemented yet");
	}
	
	// TODO - continuare con i test qui 

}
