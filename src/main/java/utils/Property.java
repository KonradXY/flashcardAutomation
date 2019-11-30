package main.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

	public static String OUTPUT_DIR = getOutputDir();
	public static String INPUT_DIR = getInputDir();
	
	public static final String LANGUAGE_LEARNING_DIR = "languageLearning/prepositions/";
	public static final String EVERNOTE_DIR = "evernote/";
	public static final String KINDLE_DIR = "kindle/";
	public static final String WEB_CRAWLER_DIR = "webcrawler/";
	public static final String TXT_INPUTDIR = "txtSummarize/";
	public static final String GENERIC_DIR = "generic/parole.txt";
	
	public static int SIMPLE_PARSER_ESP_FIELD = getSimpleParserEspField();
	public static int SIMPLE_PARSER_ENG_FIELD = getSimpleParserEngField();

	private static final String propDir = "src/main/resources/input.properties";
	private static Properties prop;

	private static String getInputDir() {
		return getProperties().getProperty("input.dir");
	}

	private static String getOutputDir() {
		return getProperties().getProperty("output.dir");
	}

	private static int getSimpleParserEspField() {
		return Integer.parseInt(getProperties().getProperty("simpleparser.engField"));
	}
	
	private static int getSimpleParserEngField() {
		return Integer.parseInt(getProperties().getProperty("simpleparser.espField"));
	}

	
	
	
	private static Properties getProperties() {
		try {
			if (prop == null) {
				loadProperties();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return prop;
	}

	private static void loadProperties() throws IOException {
		try (InputStream input = new FileInputStream(propDir)) {
			prop = new Properties();
			prop.load(input);
		}
	}
}
