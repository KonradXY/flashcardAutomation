package main.java.engines;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.model.parsers.IParser;
import main.java.model.printers.IPrinter;
import main.java.model.readers.IReader;
import main.java.model.deck.AnkiDeck;

public abstract class TextEngine extends AnkiEngine {

	private final static Logger log = Logger.getLogger(TextEngine.class);

	protected IReader reader;
	protected IParser parser;
	protected IPrinter printer;

	protected TextEngine(String input, String output) { super(input, output); }

	public IReader getReader() 						{ return reader; }
	public IParser getParser() 						{ return parser; }
	public IPrinter getPrinter() 					{ return printer; }

	public void setReader(IReader reader) 			{ this.reader = reader; }
	public void setParser(IParser parser) 			{ this.parser = parser; }
	public void setPrinter(IPrinter printer) 		{ this.printer = printer; }


	@Override
	public void createFlashcards() { 
		Map<Path, String> contentRead = this.read(this.getInputAsPath());
		List<AnkiDeck> cardMap = this.parse(contentRead);
		cardMap.forEach(entry -> this.print(entry));
	}

	// ***************** Read Functions
	public Map<Path, String> read(Path file) {
		try {
			return this.getReader().readFile(file); 
		} catch (IOException ex) {
			log.error("Errore durante la lettura del file: " + file, ex);
			throw new IllegalStateException(ex);
		}
	}

	// ***************** Parse & Sort Functions
	public List<AnkiDeck> parse(Map<Path, String> content) {
		List<AnkiDeck> decks = new ArrayList<>();
		for (Map.Entry<Path, String> singleContent : content.entrySet()) {
			decks.addAll(this.parser.parse(singleContent.getKey(), singleContent.getValue(), this.getOutputDir()));	
		}

		// FIXME- passo l'output folder e il titolo del file all'interno dei deck creati dopo il parsing (questa roba dovrebbe essere molto piu' organica)
		decks = setDestFoldersAndTitleForDecks(decks);
		
		return decks;
	}



	// ***************** Print Functions
	public void print(List<AnkiDeck> entry) {
		entry.forEach(it -> print(it));
	}
	private void print(AnkiDeck deck) {
		try {
			this.getPrinter().printFile(deck);
		} catch (IOException ex) {
			log.error("Errore durante la scrittura del file: " + deck.getPathDest(), ex);
			throw new IllegalStateException(ex);
		}
	}


	// ***************** Utility Functions

	private List<AnkiDeck> setDestFoldersAndTitleForDecks(List<AnkiDeck> content) {
		content.stream().forEach(it -> {
			it.setDestFolder(this.getOutputDir());
		});
		return content;
	}





}
