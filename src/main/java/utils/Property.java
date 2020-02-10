package main.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

	public static String OUTPUT_DIR = getOutputDir();
	public static String INPUT_DIR = getInputDir();


	public static final String LANGUAGE_LEARNING_PATH = "languageLearning/prepositions/";
	public static final String EVERNOTE_PATH = "evernote/";
	public static final String KINDLE_PATH = "kindle/";
	public static final String WEB_CRAWLER_PATH = "webcrawler/vocabulary_building_b1/vocabolario b1 citta.txt";
	public static final String TXT_INPUT_PATH = "txtSummarize/";
	public static final String GENERIC_PATH = "generic/parole.txt";
	public static final String STOPWORD_PATH = "stopwords/";



	private static final String propDir = "src/main/resources/input.properties";
	private static Properties prop;

	private static String getInputDir() {
		return getProperties().getProperty("input.dir");
	}
	private static String getOutputDir() {
		return getProperties().getProperty("output.dir");
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
