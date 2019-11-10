package main.java.facade;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.abstractModel.AbstractReader;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.utils.FlashcardEngineFactory;

@Component
public class FlashcardFacade {
	
	private final FlashcardEngineFactory flashcardEngineFactory;
	
	private AbstractReader reader;
	private IParser parser;
	private IPrinter printer;
	
	public FlashcardFacade(@Autowired FlashcardEngineFactory flashcardFactory) {
		this.flashcardEngineFactory = flashcardFactory;
	}

	
	public void buildFlashcardsFromTextFile(Path inputContent, Path outputContent, String[] args) throws IOException {
//		flashcardEngineFactory.buildFlashcardEngine(args, this.reader, this.parser, this.printer);
//		Map<Path, String> input = reader.readFile(inputContent);
//		List<AbstractAnkiCard> parsedAnkiCards = parser.parseToAnkiFlashcard(input);
//		printer.printFile(outputContent, parsedAnkiCards);
	}
}
