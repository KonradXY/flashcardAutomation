package main.java.facade;

import com.google.inject.Inject;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.AnkiEngineFactory;
import main.java.service.SpanishMediator;
import main.java.netutilities.CertificateManager;

public class FlashcardFacade {
	
	private final AnkiEngineFactory engineBuilder;
	private final SpanishMediator languageLearningFacade;

	@Inject
	public FlashcardFacade(AnkiEngineFactory engineBuilder, SpanishMediator languageLearningMediator) {
		this.engineBuilder = engineBuilder;
		this.languageLearningFacade = languageLearningMediator;
	}
	
	public void buildFlashcardsFromTextFile(String[] args) {
		AbstractAnkiEngine ankiModel = engineBuilder.createTextEngine(args);
		ankiModel.createFlashcards();
	}

	public void buildSimpleDefinitionFlashcardsFromWeb(String inputFile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		languageLearningFacade.createDefinitionFlashcards(inputFile, outputFile);
	}
	
	public void buildFlashcardsFromWeb(String inputFile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		languageLearningFacade.createFlashcard(inputFile, outputFile);
	}

	public void buildClozeFlashcardsFromWeb(String inputfile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		languageLearningFacade.createClozeFlashcards(inputfile, outputFile);
	}
}
