package main.java.facade;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import main.java.baseModel.AbstractAnkiCard;
import main.java.baseModel.SimpleReader;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.netutilities.CertificateManager;
import main.java.utils.FlashcardEngineFactory;
import main.java.webcrawlers.LanguageLearningMediator;

public class FlashcardFacade {
	
	private static final Logger log = Logger.getLogger(FlashcardFacade.class);
	
	public static SimpleReader reader;
	public static IParser parser;
	public static IPrinter printer;

	private final LanguageLearningMediator languageLearningMediator;

	@Inject
	public FlashcardFacade(LanguageLearningMediator languageLearningMediator) {
		this.languageLearningMediator = languageLearningMediator;
	}
	
	
	public void buildFlashcardsFromTextFile(Path inputContent, Path outputContent, String[] args) throws IOException {
		FlashcardEngineFactory.buildFlashcardEngine(args);
		Map<Path, String> input = reader.readFile(inputContent);
		List<AbstractAnkiCard> parsedAnkiCards = parser.parseToAnkiFlashcard(input);
		printer.printFile(outputContent.toString(), parsedAnkiCards);
	}
	
	public void buildFlashcardsFromWeb(String inputFile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		languageLearningMediator.createFlashcard(inputFile, outputFile);
	}

	public void buildClozeFlashcardsFromWeb(String inputfile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		languageLearningMediator.createClozeFlashcards(inputfile, outputFile);
	}
}
