package main.java.engines.textengines;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.engines.TextEngine;

class DefaultAnkiEngineTest extends TextEngineTest {
	
	private static final String testFileDir = "test/main/resources/generic/";
	private static final String testFileOutput = testFileDir + "testDefaultParser_parsed.txt";
	private static final Path testFileOutputPath = Paths.get(testFileOutput);
	

	private final String entry1 = "<div class=\"front\"> a </div>	<div class=\"back\">  at </div>";
	private final String entry2 = "<div class=\"front\"> a la derecha de </div>	<div class=\"back\">  to the right of </div>";
	private final String entry3 = "<div class=\"front\"> a la izquierda de </div>	<div class=\"back\">  to the left of </div>";
	private final String entry4 = "<div class=\"front\"> a traveès de </div>	<div class=\"back\">  across </div>";
	private final String entry5 = "<div class=\"front\"> al lado de </div>	<div class=\"back\">  next to, next door to </div>";
	private final String entry6 = "<div class=\"front\"> cerca de </div>	<div class=\"back\">  near, close to, by </div>";
	private final String entry7 = "<div class=\"front\"> con </div>	<div class=\"back\">  with </div>";
	private final String entry8 = "<div class=\"front\"> debajo de </div>	<div class=\"back\">  under </div>";
	private final String entry9 = "<div class=\"front\"> delante de </div>	<div class=\"back\">  in front of </div>";
	private final String entry10 = "<div class=\"front\"> dentro de </div>	<div class=\"back\">  inside </div>";
	
	private List<String> entries = Arrays.asList(entry1,entry2,entry3,entry4,entry5,entry6,entry7,entry8,entry9,entry10);
	
	private static TextEngine defaultEngine;

	
	@BeforeAll
	public static void setup() {
		defaultEngine = new DefaultAnkiEngine(testFileDir, testFileDir);
		defaultEngine.buildEngine();
	}
	
	@Test
	void testDefaultEngineWorksCorrectly() throws IOException {
		defaultEngine.createFlashcards();
		
		assertTrue(Files.exists(testFileOutputPath));
		
		String contentReadFromFile = getContentFromFile(testFileOutputPath);
		for (String entry : entries)
			assertTrue(contentReadFromFile.contains(entry));

	}
	
	@AfterAll
	public static void tearDown() throws IOException {
		if (Files.exists(testFileOutputPath)) {
			Files.delete(testFileOutputPath);
		}
	}

}
