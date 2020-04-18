package main.java.engines.textengines;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.engines.TextEngine;

class LanguageLearningEngineTest extends TextEngineTest{
	
	private static final String testFileDir = "test/main/resources/language_learning/";

	private static final String outputGramamtica  = testFileDir + "grammatica_parsed.txt";
	private static final String outputTraduzioni  = testFileDir + "traduzioni_parsed.txt";
	private static final String outputVocabolario = testFileDir + "vocabolario_parsed.txt";

	private static TextEngine languageLearningEngine;
	
	private final String entry1 = "<div class=\"front\"> ﻿Domanda di grammatica </div>	<div class=\"back\">  Risposta di grammatica - elenco 1 - elenco 2 </div>";
	private final String entry2 = "<div class=\"front\">  What time will you go to bed? </div>	<div class=\"back\">  ¿A qué hora te acostarás? </div>";
	private final String entry3 = "<div class=\"front\">  We will arrive at ten o’clock tomorrow night. </div>	<div class=\"back\">  Llegaremos a las diez mañana por la noche. </div>";
	private final String entry4 = "<div class=\"front\">  He will sleep until tomorrow afternoon. </div>	<div class=\"back\">  Él dormirá hasta mañana por la tarde. </div>";
	private final String entry5 = "<div class=\"front\">  She’ll buy a new car next year. </div>	<div class=\"back\">  Ella comprará un carro nuevo el año que viene. </div>";
	private final String entry6 = "<div class=\"front\">  I’ll speak with you tomorrow. </div>	<div class=\"back\">  Te hablaré mañana. or Hablaré contigo mañana. </div>";
	private final String entry7 = "<div class=\"front\"> ﻿ Test da tradurre </div>	<div class=\"back\">  testo tradotto </div>";
	
	private final List<String> entryVocabolario = Arrays.asList(entry2, entry3, entry4, entry5, entry6);
	private final List<String> entryGrammatica = Arrays.asList(entry1);
	private final List<String> entryTraduzione = Arrays.asList(entry7);

	@BeforeAll
	public static void setup() {
		languageLearningEngine = new LanguageLearningEngine(testFileDir, testFileDir);
		languageLearningEngine.buildEngine();
	}

	@Test
	void testCorrectCreationOfLanguageLearningCards() throws IOException {
		languageLearningEngine.createFlashcards();

		String contentReadFromFile = getContentFromFile(Paths.get(outputGramamtica));
		for (String entry : entryGrammatica)
			assertTrue(contentReadFromFile.contains(entry));

		contentReadFromFile = getContentFromFile(Paths.get(outputTraduzioni));
		for (String entry : entryTraduzione)
			assertTrue(contentReadFromFile.contains(entry));

		contentReadFromFile = getContentFromFile(Paths.get(outputVocabolario));
		for (String entry : entryVocabolario)
			assertTrue(contentReadFromFile.contains(entry));


	}
	
	@AfterAll
	public static void tearDown() throws IOException {
		Files.deleteIfExists(Paths.get(outputGramamtica));
		Files.deleteIfExists(Paths.get(outputTraduzioni));
		Files.deleteIfExists(Paths.get(outputVocabolario));
	}
	
}
