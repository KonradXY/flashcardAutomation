package main.java.facade;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import main.java.factory.AbstractAnkiEngine;
import main.java.factory.EngineBuilder;
import main.java.model.AbstractAnkiCard;
import main.java.netutilities.CertificateManager;
import main.java.webcrawlers.LanguageLearningMediator;

public class FlashcardFacade {
	
	private final EngineBuilder engineBuilder;
	private final LanguageLearningMediator languageLearningMediator;
	private AbstractAnkiEngine ankiModel;
	

	
	@Inject
	public FlashcardFacade(EngineBuilder engineBuilder, LanguageLearningMediator languageLearningMediator) {
		this.engineBuilder = engineBuilder;
		this.languageLearningMediator = languageLearningMediator;
	}
	
	public void buildFlashcardsFromTextFile(String[] args) {
		ankiModel = engineBuilder.createTextEngine(args);
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
