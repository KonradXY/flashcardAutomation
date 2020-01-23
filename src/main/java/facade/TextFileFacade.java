package main.java.facade;

import com.google.inject.Inject;

import main.java.enginefactory.AbstractAnkiEngine;
import main.java.enginefactory.AnkiEngineFactory;
import main.java.service.SpanishWebCrawlerService;

import java.util.List;

public class TextFileFacade {
	
	private final AnkiEngineFactory engineBuilder;
	private final SpanishWebCrawlerService spanishWebCrawlerService;

	@Inject
	public TextFileFacade(AnkiEngineFactory engineBuilder, SpanishWebCrawlerService spanishWebCrawlerService) {
		this.engineBuilder = engineBuilder;
		this.spanishWebCrawlerService = spanishWebCrawlerService;
	}
	
	public void buildFlashcardsFromTextFile(List<String> args) {
		AbstractAnkiEngine ankiModel = engineBuilder.createTextEngine(args);
		ankiModel.createFlashcards();
	}

}
