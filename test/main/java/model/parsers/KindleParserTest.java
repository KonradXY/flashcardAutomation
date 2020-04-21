package main.java.model.parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.contracts.IParser;
import main.java.contracts.IReader;
import main.java.model.AnkiDeck;
import main.java.model.readers.TextFileReader;
import main.java.strategy.ReadingFormatStrategy;

class KindleParserTest {
	
	private final static String testFileDir = "test/main/resources/kindle/";
	private final static String testFileOutput = "test/main/resources/kindle/testKindleEngine.txt";
	private final static Path testFileOutputPath = Paths.get(testFileOutput);
	
	private static IReader kindleReader;
	private static IParser kindleParser;
	
	private static Map<Path, String> inputContent;

	@BeforeAll
	public static void setup() throws IOException {
		kindleReader = new TextFileReader(ReadingFormatStrategy.REPLACE_NEW_LINES);
		inputContent = kindleReader.readFile(Paths.get(testFileDir));
		kindleParser = new KindleParser();
	}
	
	@Test
	void test() {
		List<AnkiDeck> decks = parse(inputContent, testFileOutput);
		assertEquals(2, decks.size());
		
	}
	
	
	private List<AnkiDeck> parse(Map<Path, String> inputContent, String destFolder) {
		List<AnkiDeck> decks = new ArrayList<>();
		for (Map.Entry<Path, String> singleContent : inputContent.entrySet()) {
			decks.addAll(kindleParser.parse(singleContent.getKey(), singleContent.getValue(), destFolder));	
		}

		return decks;
	}
	

}
