package main.java.facade;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import main.java.baseModel.AbstractAnkiCard;
import main.java.baseModel.SimpleReader;
import main.java.config.SpringConfig;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.netutilities.CertificateManager;
import main.java.utils.FlashcardEngineFactory;
import main.java.webcrawlers.LanguageLearningMediator;

@Component
public class FlashcardFacade {
	
	private static final Logger log = Logger.getLogger(FlashcardFacade.class);
	
	public static SimpleReader reader;
	public static IParser parser;
	public static IPrinter printer;
	
	
	public static void buildFlashcardsFromTextFile(Path inputContent, Path outputContent, String[] args) throws IOException {
		FlashcardEngineFactory.buildFlashcardEngine(args);
		Map<Path, String> input = reader.readFile(inputContent);
		List<AbstractAnkiCard> parsedAnkiCards = parser.parseToAnkiFlashcard(input);
		printer.printFile(outputContent.toString(), parsedAnkiCards);
	}
	
	public static void buildFlashcardsFromWeb(ApplicationContext ctx, String inputFile, String outputFile) throws Exception {
		LanguageLearningMediator languageLearningMediator = ctx.getBean(LanguageLearningMediator.class);

		CertificateManager.doTrustToCertificates();
		languageLearningMediator.parse(inputFile, outputFile);
		
	}
}
