package main.java.model.evernote;

import main.java.contracts.IAnkiCard;
import main.java.engines.TextEngine;
import main.java.engines.textengines.EvernoteEngine;
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
						+ "<br> "
					+ "</div>"
				+ "</div>";
	private static final String emptyCardValueBack = 
			"<div class=\"back\"> "
				+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
					+ "<br> "
				+ "</div>"
			+ "</div>";
	
	private static final String emptyCardValue = emptyCardValueFront + "\t" + emptyCardValueBack + "\n";


	
	private static final String testFileDir = "test/main/resources/";
	private static final String testMediaFolderDir = testFileDir + "mediaFolder/";
	private static final String testFile = testFileDir + "testevernote.html"; 
	private static final String outputFile = testFileDir + "outputtest.txt";
	
	private static final Path testFilePath = Paths.get(testFile);
	private static final Path outputTestFile = Paths.get(outputFile);
	
	private static final Path imgPath1 = Paths.get(testMediaFolderDir+"testevernoteimmagine1.jpg");
	private static final Path imgPath2 = Paths.get(testMediaFolderDir+"testevernoteimmagine2.jpg");
	private static final Path imgPath3 = Paths.get(testMediaFolderDir+"testevernoteimmagine3.jpg");

	private static TextEngine evernoteEngine;
	
	
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
		
		assertEquals(4, cardList.size());
		
		IAnkiCard emptyCard = null, imgCard1 = null, imgCard2 = null, imgCard3 = null;
		for (IAnkiCard card : cardList) {
			switch (card.getFront().text().trim()) {
				case "immagine1" : imgCard1 = card; break;
				case "immagine2" : imgCard2 = card; break;
				case "immagine3" : imgCard3 = card; break;
				case "" 		 : emptyCard = card; break;
				default : throw new RuntimeException("card not recognized !");
			}
		}
		
		assertEquals(replaceWhitespaces(emptyCardValue.trim()), replaceWhitespaces(emptyCard.toString().trim()));

		assertEquals(replaceWhitespaces(getImgValue("testevernoteimmagine1.jpg", "immagine1", "immagine1.jpg").trim()),
				replaceWhitespaces(imgCard1.toString().trim()));

		assertEquals(replaceWhitespaces(getImgValue("testevernoteimmagine2.jpg", "immagine2", "immagine2.jpg").trim()),
				replaceWhitespaces(imgCard2.toString().trim()));

		assertEquals(replaceWhitespaces(getImgValue("testevernoteimmagine3.jpg", "immagine3", "immagine3.jpg").trim()),
				replaceWhitespaces(imgCard3.toString().trim()));

		assertTrue(Files.exists(imgPath1));
		assertTrue(Files.exists(imgPath2));
		assertTrue(Files.exists(imgPath3));

		Files.delete(imgPath1); Files.delete(imgPath2); Files.delete(imgPath3);
	}
	
	@Test
	void testEvernoteEnginePrinting() throws IOException {
		Map<Path, String> content = evernoteEngine.read(testFilePath);
		List<IAnkiCard> cardList = evernoteEngine.parse(content);
		evernoteEngine.print(cardList, outputTestFile);
		
		assertTrue(Files.exists(outputTestFile));
		Files.delete(imgPath1);
		Files.delete(outputTestFile);
	}

	@AfterAll
	public static void teardown() throws IOException {
		Files.deleteIfExists(imgPath1);
		Files.deleteIfExists(imgPath2);
		Files.deleteIfExists(imgPath3);
		Files.deleteIfExists(outputTestFile);
	}


	private String replaceWhitespaces(String input) { return input.replace(" ", "");}

	private String getImgValue(String imgSrc, String imgName, String imgFileName) {
		String imgFront =
				"<div class=\"front\"> "
						+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
						+ imgName
						+ "</div>"
						+ "</div>" ;

		String imgBack =
				"<div class=\"back\"> "
						+ "<div align=\"left\" text-align=\"left\" font style=\"font-size: 10pt\" margin=\"auto\">  "
						+ "<img src=\""
						+ imgSrc
						+ "\" type=\"image/jpeg\" data-filename=\""
						+ imgFileName
						+ "\"> "
						+ "</div>"
						+ "</div>";
		return imgFront + "\t" + imgBack + "\n";
	}

}
