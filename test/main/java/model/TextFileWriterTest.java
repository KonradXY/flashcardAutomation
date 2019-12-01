package main.java.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;

@RunWith(JUnitPlatform.class)
class TextFileWriterTest {

	private static AnkiCard card;
	private static final Path filetestpath = Paths.get("./test.txt");
	private IPrinter printer = new TextFilePrinter();
	
	@Test
	void testSimpleCardCorrectlyPrinted() throws IOException {
		card = new AnkiCard("this is the front", "this is the back");
		List<IAnkiCard> cardList = Arrays.asList(card);
		printer.printFile(filetestpath, cardList);
	}

}
