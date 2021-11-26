package main.java.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import main.java.model.readers.TextFileReader;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.model.readers.IReader;
import main.java.model.readers.ReadStrategy;

class TextFileReaderTest {

	private final static Logger log = Logger.getLogger(TextFileReaderTest.class);
	
	private final static String filename1 = "tempfile1.txt";
	private final static String filename2 = "tempfile2.txt";
	private final static String filedirname = "filedir";
	
	private final static String innerfile = "innerfile.txt";
	private final static String innerdir = "innerdir";
	
	private final static Path pathdir = Paths.get(filedirname);
	private final static Path pathfile1 = pathdir.resolve(Paths.get(filename1));
	private final static Path pathfile2 = pathdir.resolve(Paths.get(filename2));
	
	private final static Path pathinnerdir = pathdir.resolve(Paths.get(innerdir));
	private final static Path pathinnerfile = pathinnerdir.resolve(Paths.get(innerfile));
	
	private final static String content = "This is \n the content \n";

	private final IReader reader = new TextFileReader();

	@BeforeAll
	public static void setup() throws IOException {
		Files.createDirectories(pathinnerdir);
		Files.write(pathfile1, content.getBytes());
		Files.write(pathfile2, content.getBytes());
		Files.write(pathinnerfile, content.getBytes());
	}
	
	@Test
	void testReadingSingleFile() throws IOException {
		Map<Path, String> contentRead = reader.readFile(pathfile1);
		assertTrue(contentRead.size() == 1);
		assertTrue(contentRead.get(pathfile1) != null);
	}
	
	@Test
	void testReadingDirectoryRecursively() throws IOException {
		Map<Path, String> contentRead = reader.readFile(pathdir);
		assertTrue(contentRead.size() == 3);
		assertTrue(contentRead.get(pathfile1) != null);
		assertTrue(contentRead.get(pathfile2) != null);
		assertTrue(contentRead.get(pathinnerfile) != null);
	}
	
	@Test
	void testReadingDirectory() throws IOException {
		Map<Path, String> contentRead = reader.readFile(pathinnerdir);
		assertTrue(contentRead.size() == 1);
		assertTrue(contentRead.get(pathinnerfile) != null);
	}
	
	
	
	@AfterAll
	public static void tearDown() throws IOException {
		Files.delete(pathinnerfile);
		Files.delete(pathinnerdir);
		Files.delete(pathfile1);
		Files.delete(pathfile2);
		Files.delete(pathdir);
		log.info("teardown completed");
	}
	
	

}
