package main.java.model.languageLearning;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.LanguageLearningAnkiEngine;

class LanguageLearningParserTest {
	
	private static final String testFileDir = "test/main/resources/language_learning/";
	private static final String outputFile = testFileDir + "outputtest.txt";
	private static final Path outputFilePath = Paths.get(outputFile);
	
	private static AbstractAnkiEngine languageLearningEngine;
	
	private final String entry1 = "<div class=\"front\"> ﻿Domanda di grammatica </div>	<div class=\"back\">  Risposta di grammatica - elenco 1 - elenco 2 </div>";
	private final String entry2 = "<div class=\"front\">  What time will you go to bed? </div>	<div class=\"back\">  ¿A qué hora te acostarás? </div>";
	private final String entry3 = "<div class=\"front\">  We will arrive at ten o’clock tomorrow night. </div>	<div class=\"back\">  Llegaremos a las diez mañana por la noche. </div>";
	private final String entry4 = "<div class=\"front\">  He will sleep until tomorrow afternoon. </div>	<div class=\"back\">  Él dormirá hasta mañana por la tarde. </div>";
	private final String entry5 = "<div class=\"front\">  She’ll buy a new car next year. </div>	<div class=\"back\">  Ella comprará un carro nuevo el año que viene. </div>";
	private final String entry6 = "<div class=\"front\">  I’ll speak with you tomorrow. </div>	<div class=\"back\">  Te hablaré mañana. or Hablaré contigo mañana. </div>";
	private final String entry7 = "<div class=\"front\"> ﻿ Test da tradurre </div>	<div class=\"back\">  testo tradotto </div>";
	
	private final List<String> entries = Arrays.asList(entry1, entry2, entry3, entry4, entry5, entry6, entry7);
	
	@BeforeAll
	public static void setup() {
		languageLearningEngine = new LanguageLearningAnkiEngine(testFileDir, outputFile);
		languageLearningEngine.setInputDir("./");
		languageLearningEngine.setOutputDir("./");
		languageLearningEngine.buildEngine();
	}

	@Test
	void testCorrectCreationOfLanguageLearningCards() throws IOException {
		languageLearningEngine.createFlashcards();
		
		String contentReadFromFile = getContentFromFile(outputFilePath);
		for (String entry : entries) {
			assertTrue(contentReadFromFile.contains(entry));
		}
		
	}
	
	@AfterAll
	public static void tearDown() throws IOException {
		Files.deleteIfExists(outputFilePath);
	}
	
	private String getContentFromFile(Path filePath) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toString()), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		br.lines().forEach(sb::append);
		br.close();
		return sb.toString();
	}
}
