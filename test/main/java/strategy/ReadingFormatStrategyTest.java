package main.java.strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.contracts.IReader;
import main.java.model.readers.TextFileReader;


class ReadingFormatStrategyTest {

	private final static String filename = "tempfile.txt";
	private final static Path filepath = Paths.get(filename);
	private final static String content = "This is \n the content \n";

	private IReader reader;

	@BeforeAll
	public static void setup() throws IOException {
		createTempFile(filepath, filename, content);
	}

	@Test
	void testAddPipeReadingStrategy() throws IOException {
		String check = content.replaceAll("\n", "\n|");
		reader = new TextFileReader(ReadingFormatStrategy.ADD_PIPE);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals(check, contentRead.get(filepath));
	}
	
	@Test
	void testNoFormatStrategy() throws IOException {
		String check = content;
		reader = new TextFileReader(ReadingFormatStrategy.NO_FORMAT);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals(check, contentRead.get(filepath));
	}
	
	@Test
	void testReplaceNewLinesFormatStrategy() throws IOException {
		String check = content.replaceAll("\n", "");
		reader = new TextFileReader(ReadingFormatStrategy.REPLACE_NEW_LINES);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals(check, contentRead.get(filepath));
	}
	
	@Test
	void testAddNewLineFormatStrategy() throws IOException {
		String check = content.replace("\n", "\n\n");
		reader = new TextFileReader(ReadingFormatStrategy.ADD_NEW_LINE);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals(check, contentRead.get(filepath));
	}


	@AfterAll
	public static void tearDown() throws IOException {
		Files.delete(filepath);
	}


	private static void createTempFile(Path filepath, String filename, String content) throws IOException {
		Files.write(filepath, content.getBytes());
	}
}
