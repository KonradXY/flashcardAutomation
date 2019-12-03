package main.java.model.evernote;

import main.java.contracts.IAnkiCard;
import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.EvernoteEngine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvernoteHtmlParserTest {
	
	private static final String emptyCardValueFront = 
			"<div class=\"front\"> "
					+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
						+ "D:&nbsp; &nbsp; "
					+ "</div>"
				+ "</div>";
	private static final String emptyCardValueBack = 
			"<div class=\"back\"> "
				+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
					+ "R:&nbsp; &nbsp; "
				+ "</div>"
			+ "</div>";
	
	private static final String emptyCardValue = emptyCardValueFront + "\t" + emptyCardValueBack + "\n";

	private static final String imgCardValueFront = 
			"<div class=\"front\"> "
				+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
					+ "D:&nbsp; &nbsp;immagine "
				+ "</div>"
			+ "</div>" ;
			
			
	
	private static final String imgCardValueBack = 
		 	"<div class=\"back\"> "
		 		+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
		 			+ "R:&nbsp; &nbsp;  <img src=\"testevernotedownload.jpg\" type=\"image/png\" data-filename=\"download.png\"> "
		 		+ "</div>"
		 	+ "</div>";
	
	private static final String imgCardValue = imgCardValueFront + "\t" + imgCardValueBack + "\n";
	

	
	private static final String testFileDir = "test/main/resources/";
	private static final String testFile = testFileDir + "testevernote.html"; 
	private static final String outputFile = testFileDir + "outputtest.txt";
	
	private static final Path testFilePath = Paths.get(testFile);
	private static final Path outputTestFile = Paths.get(outputFile);
	
	private static final Path imgPath = Paths.get(testFileDir+"testevernotedownload.jpg");
	
	private static AbstractAnkiEngine evernoteEngine;
	
	
	@BeforeAll
	public static void setup() {
		evernoteEngine = new EvernoteEngine(testFile, testFileDir);
		evernoteEngine.setInputDir("./");
		evernoteEngine.setOutputDir("./");
		evernoteEngine.buildEngine();
	}
	
	@Test
	void testEvernoteEngineReading() throws IOException {
		Map<Path, String> content = evernoteEngine.read(testFilePath);
		assertTrue(content.size() == 1);
		assertTrue(content.containsKey(testFilePath));
	}
	
	@Test 
	void testEvernoteEngineParsing() throws IOException {
		Map<Path, String> content = evernoteEngine.read(testFilePath);
		List<IAnkiCard> cardList = evernoteEngine.parse(content);
		
		assertEquals(2, cardList.size());
		
		IAnkiCard emptyCard, imgCard;
		if (cardList.get(0).toString().length() > cardList.get(1).toString().length()) {
			emptyCard = cardList.get(1);
			imgCard = cardList.get(0);
		} else {
			emptyCard = cardList.get(0);
			imgCard = cardList.get(1);
		}
		
		assertEquals(emptyCardValue.trim(), emptyCard.toString().trim());
		assertEquals(imgCardValue.trim(), imgCard.toString().trim());
		assertTrue(Files.exists(imgPath));

		Files.delete(imgPath);
	}
	
	@Test
	void testEvernoteEnginePrinting() throws IOException {
		Map<Path, String> content = evernoteEngine.read(testFilePath);
		List<IAnkiCard> cardList = evernoteEngine.parse(content);
		evernoteEngine.print(cardList, outputFile);
		
		assertTrue(Files.exists(outputTestFile));
		Files.delete(imgPath);
		Files.delete(outputTestFile);
	}

	@AfterAll
	public static void teardown() throws IOException {
		Files.deleteIfExists(imgPath);
		Files.deleteIfExists(outputTestFile);
	}
}
