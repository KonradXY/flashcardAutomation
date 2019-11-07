package main.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

	public static String OUTPUT_DIR = getOutputDir();
	public static String INPUT_DIR = getInputDir();
	public static String EVERNOTE_DIR = getEvernoteFolder();
	public static String KINDLE_DIR = getKindleDir();
	public static String LANGUAGE_LEARNING_DIR = getLanguageLearningDir();
	public static String WEB_CRAWLER_DIR = getWebCrawlerDir();
	public static String TXT_INPUTDIR = getTxtInputDir();
	public static String GENERIC_DIR = getGenericDir();

	private static final String propDir = "src/main/resources/input.properties";
	private static Properties prop;

	private static String getInputDir() {
		return getProperties().getProperty("input.dir");
	}

	private static String getOutputDir() {
		return getProperties().getProperty("output.dir");
	}

	private static String getEvernoteFolder() {
		return getProperties().getProperty("evernote.dir");
	}

	private static String getKindleDir() {
		return getProperties().getProperty("kindle.dir");
	}

	private static String getLanguageLearningDir() {
		return getProperties().getProperty("languageLearning.dir");
	}

	private static String getWebCrawlerDir() {
		return getProperties().getProperty("webcrawler.dir");
	}

	private static String getTxtInputDir() {
		return getProperties().getProperty("txtinput.dir");
	}

	private static String getGenericDir() {
		return getProperties().getProperty("generic.dir");
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
