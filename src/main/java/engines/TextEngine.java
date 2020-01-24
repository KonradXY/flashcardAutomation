package main.java.engines;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;

public abstract class TextEngine extends AbstractEngine {
	
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

	
	// TODO - per poter fare il discorso della lettura e creazione di piu' file contemporeaneamente dovrei far
	// ritornare dal parser una mappa di filePath, List<AnkiCard> in modo da poterli scrivere separatamente. 
	// Inoltre dovrei fare dei test !

	public void createFlashcards() {
		Map<Path, String> contentRead = this.read(this.getFullInputPath());
		List<IAnkiCard> cardList = this.parse(contentRead);
		this.print(cardList, this.getFullOutputPath());
	}

	public List<IAnkiCard> parse(Map<Path, String> content) {
		return this.getParser().parse(content);
	}

	public Map<Path, String> read(Path file) {
		try {
			return this.getReader().readFile(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void print(List<IAnkiCard> cardList, Path destPath) {
		try {
			this.getPrinter().printFile(destPath, cardList);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	

	

}
