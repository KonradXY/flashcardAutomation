package main.java.strategy;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import main.java.contracts.IReader;
import main.java.model.simplemodel.SimpleReader;


@RunWith(JUnitPlatform.class)
class FormatStrategyTest {

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
		reader = new SimpleReader(FormatStrategy.ADD_PIPE);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals("the 'add pipe' format strategy should add a pipe", 
					check, contentRead.get(filepath));
	}
	
	@Test
	void testNoFormatStrategy() throws IOException {
		String check = content;
		reader = new SimpleReader(FormatStrategy.NO_FORMAT);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals("the 'no format' format strategy should be identical as the input !", 
					check, contentRead.get(filepath));
	}
	
	@Test
	void testReplaceNewLinesFormatStrategy() throws IOException {
		String check = content.replaceAll("\n", "");
		reader = new SimpleReader(FormatStrategy.REPLACE_NEW_LINES);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals("the 'replace new lines' format strategy should replace new lines !", 
					check, contentRead.get(filepath));
	}
	
	@Test
	void testAddNewLineFormatStrategy() throws IOException {
		String check = content.replace("\n", "\n\n");
		reader = new SimpleReader(FormatStrategy.ADD_NEW_LINE);
		Map<Path, String> contentRead = reader.readFile(filepath);
		assertEquals("the 'add new line' format strategy should add a new line at the end !", 
					check, contentRead.get(filepath));
	}


	@AfterAll
	public static void tearDown() throws IOException {
		Files.delete(filepath);
	}


	private static void createTempFile(Path filepath, String filename, String content) throws IOException {
		Files.createFile(filepath);
		BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
		bf.write(content);
		bf.flush();
		bf.close();
	}
}
