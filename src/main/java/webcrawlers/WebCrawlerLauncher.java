package main.java.webcrawlers;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;
import static main.java.utils.Property.WEB_CRAWLER_DIR;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.java.config.SpringConfig;
import main.java.netutilities.CertificateManager;

public class WebCrawlerLauncher {

	private static final Logger log = Logger.getLogger(WebCrawlerLauncher.class);
	
	private static final String INPUT_FILE  	= INPUT_DIR + WEB_CRAWLER_DIR + "2000_parole_lista_2.txt";
	private static final String ES_STOPWORDS 	= INPUT_DIR + WEB_CRAWLER_DIR + "stopwords-es.txt";
	private static final String OUTPUT_FILE 	= OUTPUT_DIR+ WEB_CRAWLER_DIR + "scrapedList.txt";

	

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		LanguageLearningMediator languageLearningMediator = ctx.getBean(LanguageLearningMediator.class);
		
		long t = System.currentTimeMillis();
		CertificateManager.doTrustToCertificates();

		log.info("Start crawling");
		
		languageLearningMediator.parse(INPUT_FILE, OUTPUT_FILE);
		
		log.info("Parsing completed ! Time elapsed: " + (System.currentTimeMillis() - t) / 1000 + " sec");
		

	}
	
	
	
}
