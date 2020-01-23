package main.java.facade;

import com.google.inject.Inject;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.AnkiEngineFactory;
import main.java.service.SpanishWebService;
import main.java.netutilities.CertificateManager;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FlashcardFacade {
	
	private final AnkiEngineFactory engineBuilder;
	private final SpanishWebService spanishWebService;

	@Inject
	public FlashcardFacade(AnkiEngineFactory engineBuilder, SpanishWebService spanishWebService) {
		this.engineBuilder = engineBuilder;
		this.spanishWebService = spanishWebService;
	}
	
	public void buildFlashcardsFromTextFile(String[] args) {
		AbstractAnkiEngine ankiModel = engineBuilder.createTextEngine(args);
		ankiModel.createFlashcards();
	}



	public void buildSimpleDefinitionFlashcardsFromWeb(String inputFile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		spanishWebService.createDefinitionFlashcards(inputFile, outputFile);
	}
	
	public void buildFlashcardsFromWeb(String inputFile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		spanishWebService.createFlashcard(inputFile, outputFile);
	}

	public void buildClozeFlashcardsFromWeb(String inputfile, String outputFile) throws Exception {
		CertificateManager.doTrustToCertificates();
		spanishWebService.createClozeFlashcards(inputfile, outputFile);
	}

	public void metodoCheWrappaLaRobaFuoriDalSpanishMediator(String inputFile, String outputFile) {

		// TODO - la roba deve funzionare in questa maniera:
		/*
			1. Prendere in input le parole da aprsare (questo viene fatto in una botta sola)

			2. Per ogni parola
				2.1 Effettuare lo scraping e la creazione degli oggetti AnkiCard
				2.2 Nel caso decorare
				2.3 Scrivere su file	// TODO - la scrittura sarebbe utile farla asincrona

		 */

		// TODO - dovrei capire dove piazzarlo. In piu' vorrei modellare sta roba in stile TextEngine (stile metodo createFlashCard praticamente)

		int numWords = 0;

		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {

		} catch(IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);	// TODO <--- vedere meglio la gestione
		}
	}
}
