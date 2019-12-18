package main.java.facade;

import com.google.inject.Inject;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.AnkiEngineFactory;
import main.java.netutilities.CertificateManager;
import main.java.webcrawlers.LanguageLearningWebCrawlerMediator;

public class FlashcardFacade {
	
	private final AnkiEngineFactory engineBuilder;
	private final LanguageLearningWebCrawlerMediator languageLearningMediator;

	@Inject
	public FlashcardFacade(AnkiEngineFactory engineBuilder, LanguageLearningWebCrawlerMediator languageLearningMediator) {
		this.engineBuilder = engineBuilder;
		this.languageLearningMediator = languageLearningMediator;
	}
	
	public void buildFlashcardsFromTextFile(String[] args) {
		AbstractAnkiEngine ankiModel = engineBuilder.createTextEngine(args);
		ankiModel.createFlashcards();
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
