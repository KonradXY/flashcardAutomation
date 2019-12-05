package main.java.facade;

import com.google.inject.Inject;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.TextEngineBuilder;
import main.java.netutilities.CertificateManager;
import main.java.webcrawlers.LanguageLearningMediator;

public class FlashcardFacade {
	
	private final TextEngineBuilder engineBuilder;
	private final LanguageLearningMediator languageLearningMediator;

	@Inject
	public FlashcardFacade(TextEngineBuilder engineBuilder, LanguageLearningMediator languageLearningMediator) {
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
