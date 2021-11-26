package main.java.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

	public static String OUTPUT_DIR = getOutputDir();
	public static String INPUT_DIR = getInputDir();
	public static String ANKI_MEDIA_COLLECTION_DIR = getAnkiMediaCollectionDir();


	public static final String LANGUAGE_LEARNING_PATH = "languageLearning/prepositions/";
	public static final String EVERNOTE_PATH = "evernoteExports/";
	public static final String KINDLE_PATH = "kindle/";
	public static final String GENERIC_PATH = "generic/ocp d_r_automatiche.txt";

	private static final String propDir = "src/main/resources/input.properties";
	private static Properties prop;

	private static String getInputDir() {
		return getProperties().getProperty("input.dir");
	}
	private static String getOutputDir() {
		return getProperties().getProperty("output.dir");
	}
	private static String getAnkiMediaCollectionDir() {
		String mediaFolder = getProperties().getProperty("anki.mediadir");
		if (StringUtils.isEmpty(mediaFolder)) throw new IllegalStateException("Anki media folder property non puo essere empty. anki.mediadir: " + mediaFolder);
		return mediaFolder;
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
