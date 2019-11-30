package main.java.model.evernote; 

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.factory.AbstractAnkiEngine;
import main.java.factory.EvernoteEngine;

class EvernoteHtmlParserTest {
	
	private static final String testFileDir = "test/main/resources/";
	private static final String testFile = testFileDir + "testevernote.html"; 
	private static final Path testFilePath = Paths.get(testFile);
	
	private static AbstractAnkiEngine evernoteEngine;

	@BeforeAll
	public static void setup() {
		evernoteEngine = new EvernoteEngine(testFile, testFileDir);
		evernoteEngine.buildEngine();
	}
	
	@Test
	void testEvernoteEngineReading() throws IOException {
		Map<Path, String> content = evernoteEngine.read(testFilePath);
		assertTrue(content.size() == 1);
		assertTrue(content.containsKey(testFilePath));
	}
	
	@Test 
	void testEvernoteEngineParsing() {
		fail("not implemented yet");
	}
	
	@Test
	void testEvernoteEnginePrinting() throws IOException {
		Map<Path, String> content = evernoteEngine.getReader().readFile(testFilePath);
		
	}

}
