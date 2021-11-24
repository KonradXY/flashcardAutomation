package main.java.facade;

import java.util.List;

import com.google.inject.Inject;

import com.google.inject.Singleton;
import main.java.engines.AnkiEngine;
import main.java.engines.factories.TextEngineFactory;

@Singleton
public class TextFileFacade {
	
	private final TextEngineFactory textEngineFactory;

	@Inject
	public TextFileFacade(TextEngineFactory textEngineFactory) {
		this.textEngineFactory = textEngineFactory;
	}

	public void buildFlashcards(List<String> args) {
		AnkiEngine ankiEngine = textEngineFactory.createEngine(args);
		ankiEngine.createFlashcards();
	}

}
