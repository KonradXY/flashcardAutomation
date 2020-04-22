package main.java.engines.textengines;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.engines.TextEngine;

class KindleEngineTest {
	
	private final static String testFileDir = "test/main/resources/kindle/";
	private final static String testFileOutput = "test/main/resources/kindle/testKindleEngine.txt";
	private final static Path testFileOutputPath = Paths.get(testFileOutput);
	
	private static TextEngine kindleEngine;
	
	@BeforeAll
	static void setup() {
		kindleEngine = new KindleEngine(testFileDir, testFileDir);
		kindleEngine.buildEngine();
	}
	
	@Test
	void testKindleEngineWorksCorrectly() {
		kindleEngine.createFlashcards();
	}

}
