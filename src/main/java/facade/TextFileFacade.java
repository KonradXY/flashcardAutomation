package main.java.facade;

import com.google.inject.Inject;

import main.java.engines.TextEngine;
import main.java.engines.factories.TextEngineFactory;

import java.util.List;

public class TextFileFacade {
	
	private final TextEngineFactory engineBuilder;

	@Inject
	public TextFileFacade(TextEngineFactory engineBuilder) {
		this.engineBuilder = engineBuilder;
	}

	public void buildFlashcardsFromTextFile(List<String> args) {
		TextEngine ankiModel = (TextEngine)engineBuilder.createEngine(args);
		ankiModel.createFlashcards();
	}

}
