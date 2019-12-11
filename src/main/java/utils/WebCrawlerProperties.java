package main.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebCrawlerProperties {

	private static final String propDir = "src/main/resources/crawler.properties";
	private static Properties prop = getProperties();

	public static final int TIME_SLEEP 		= getTimeSleep();
	public static final int NUM_EXAMPLES 	= getNumberExamples();
	public static final int NUM_TRANSLATIONS = getNumberTranslations();
	public static final int LOG_COUNTER 	= 5 * NUM_EXAMPLES;
	public static final int TIMEOUT_SEC 	= getTimeoutSec();

	// TODO - questi credo sia meglio levarli dal properties e metterli nele rispettive classi 
	public final static String REVERSO_ESP_ITA_TRANSLATION_PAGE_URL = getSpanishItalianUrl();
	public final static String WORD_REFERENCE_ESP_DEFINITION_PAGE_URL = getSpanishDefinitionUrl();
	public final static String WORD_REFERENCE_ESP_SINONYMS_PAGE_URL = getSpanishSinonymousUrl();
	public final static String discardedWordsPath 	= getDiscardedWordPath();

	private static int getTimeSleep() 				{ return Integer.parseInt(prop.getProperty("webcrawler.timesleep_milli")); }
	private static int getNumberExamples()			{ return Integer.parseInt(prop.getProperty("webcrawler.numberExamples")); }
	private static int getNumberTranslations() 		{ return Integer.parseInt(prop.getProperty("webcrawler.numberTranslations"));	}
	private static int getTimeoutSec()				{ return Integer.parseInt(prop.getProperty("webcrawler.timeout_sec")); }
	private static String getSpanishItalianUrl()	{ return prop.getProperty("webcrawler.esp_ita_url"); }
	private static String getSpanishDefinitionUrl() { return prop.getProperty("webcrawler.definizione_esp");}
	private static String getSpanishSinonymousUrl() { return prop.getProperty("webcrawler.sinonimi_esp");}
	private static String getDiscardedWordPath()	{ return prop.getProperty("webcrawler.discardedPath"); }


	private static Properties getProperties() {
		if (prop == null)
			loadProperties();
		return prop;
	}

	private static void loadProperties() {
		try (InputStream input = new FileInputStream(propDir)) {
			prop = new Properties();
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


}
