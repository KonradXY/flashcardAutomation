package main.java.engines;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.model.AnkiDeck;

public abstract class TextEngine extends AbstractEngine {

	private final static Logger log = Logger.getLogger(TextEngine.class);

	protected IReader reader;
	protected IParser parser;
	protected IPrinter printer;

	protected TextEngine() { super(); }
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
		Map<Path, AnkiDeck> cardMap = this.parse(contentRead);
		cardMap.entrySet().forEach(entry -> this.print(entry.getValue()));
	}

	// ***************** Parse & Sort Functions
	public Map<Path, AnkiDeck> parse(Map<Path, String> content) {
		Map<Path, AnkiDeck> contentParsed = new HashMap<>();
		for (Map.Entry<Path, String> singleContent : content.entrySet()) {
			contentParsed.put(getParsedFileName(singleContent.getKey()), 					// key
					this.parser.parse(singleContent.getKey(), singleContent.getValue()));	// value
		}

		// FIXME- passo l'output folder e il titolo del file all'interno dei deck creati dopo il parsing (questa roba dovrebbe essere molto piu' organica)
		contentParsed = setDestFoldersAndTitleForDecks(contentParsed);
		return parser.sort(contentParsed);
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

	// ***************** Print Functions
	public void print(Map<Path, AnkiDeck> entry) {
		entry.values().forEach(it -> print(it));
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

	private Path getParsedFileName(Path inputFile) {
		String textName = inputFile.toString();
		String extension = textName.substring(textName.lastIndexOf("."));
		return Paths.get(textName.replace(extension, "_parsed" + extension));
	}

	private Map<Path, AnkiDeck> setDestFoldersAndTitleForDecks(Map<Path, AnkiDeck> content) {
		content.entrySet().stream().forEach(it -> {
			it.getValue().setDestFolder(this.getOutputDir());
			it.getValue().setTitle(getFileNameFromPath(it.getKey()));
		});
		return content;
	}

	private String getFileNameFromPath(Path pathFile) {
		return new File(pathFile.toString()).getName();
	}




}
