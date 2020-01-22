package main.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebCrawlerProperties {

	public static final int TIME_SLEEP 		= 2000;
	public static final int MAX_NUM_EXAMPLES_PER_WORD = 2;
	public static final int NUM_TRANSLATIONS = 5;
	public static final int LOG_COUNTER 	= 5 * MAX_NUM_EXAMPLES_PER_WORD;
	public static final int TIMEOUT_SEC 	= 10;

	// TODO - questi credo sia meglio levarli dal properties e metterli nele rispettive classi 
	public final static String REVERSO_ESP_ITA_TRANSLATION_PAGE_URL = "http://context.reverso.net/traduzione/spagnolo-italiano/";

	public final static String discardedWordsPath 	= "./src/main/resources/discardedWords.log";


}
