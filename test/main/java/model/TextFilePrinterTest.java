package main.java.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import main.java.model.printers.TextFilePrinter;
import main.java.strategy.PrinterStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;
import main.java.model.kindle.KindleAnkiCard;
import main.java.model.languagelearning.LanguageLearningAnkiCard;

class TextFilePrinterTest {

	private static AnkiCard card;
	private static final String fileTestPath = "./";
	private static final String fileTestName = "test.txt";
	private static final Path filetestpath = Paths.get(fileTestPath+fileTestName);
	private IPrinter printer = new TextFilePrinter(PrinterStrategy.NO_STRATEGY);
	
	private final static String contentFront = "<div class=\"front\"> this is the front</div>";
	private final static String contentBack = "<div class=\"back\"> this is the back</div>";
	private final static String content = contentFront + "\t" + contentBack;
	
	@Test
	void testSimpleCardCorrectlyPrinted() throws IOException {
		card = new AnkiCard("this is the front", "this is the back");
		AnkiDeck deck = getDeckWithCards(Arrays.asList(card), fileTestName, fileTestPath);
		printer.printFile(deck);
		assertEquals(content, getContentFromFile(filetestpath));
	}
	
	@Test
	void testLanguageCardCorrectlyPrinted() throws IOException {
		card = new LanguageLearningAnkiCard("this is the front", "this is the back");
		AnkiDeck deck = getDeckWithCards(Arrays.asList(card), fileTestName, fileTestPath);
		printer.printFile(deck);
		assertEquals(content, getContentFromFile(filetestpath));
	}
	
	@Test
	void testKindleCardCorrectlyPrinted() throws IOException {
		card = new KindleAnkiCard("this is the front", "this is the back");
		AnkiDeck deck = getDeckWithCards(Arrays.asList(card), fileTestName, fileTestPath);
		printer.printFile(deck);
		assertEquals(content, getContentFromFile(filetestpath));
	}
	
	
	@AfterAll
	public static void tearDown() throws IOException {
		Files.deleteIfExists(filetestpath);
	}
	
	
	private String getContentFromFile(Path filePath) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toString()), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		br.lines().forEach(sb::append);
		br.close();
		return sb.toString();
	}
	
	private AnkiDeck getDeckWithCards(List<IAnkiCard> cards, String title, String destFolder) {
		return new AnkiDeck.Builder().withCards(cards).withTitle(title).withDestFolder(destFolder).build();
	}

}
