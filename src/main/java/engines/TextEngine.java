package main.java.engines;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.model.AnkiDeck;

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

	
	@Override
	public void createFlashcards() {
		Map<Path, String> contentRead = this.read(this.getInputAsPath());
		Map<Path, AnkiDeck> cardMap = this.parse(contentRead);
		cardMap.entrySet().forEach(entry -> this.print(entry.getKey(), entry.getValue()));
	}


	// ***************** Parse & Sort Functions
	public Map<Path, AnkiDeck> parse(Map<Path, String> content) {
		Map<Path, AnkiDeck> contentParsed = new HashMap<>();
		for (Map.Entry<Path, String> singleContent : content.entrySet()) {
			contentParsed.put(getParsedFileName(singleContent.getKey()), this.parser.parse(singleContent.getKey(), singleContent.getValue()));
		}
		return parser.sort(contentParsed);
	}

	private Path getParsedFileName(Path inputFile) {
		String textName = inputFile.toString();
		String extension = textName.substring(textName.lastIndexOf("."));
		return Paths.get(textName.replace(extension, "_parsed.txt"));
	}




	// ***************** Read Functions
	public Map<Path, String> read(Path file) {
		try {
			return this.getReader().readFile(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}



	// ***************** Print Functions
	public void print(Map<Path, AnkiDeck> cardList) {
		cardList.entrySet().forEach(it -> this.print(it.getKey(), it.getValue()));
	}

	public void print(Path destPath, AnkiDeck cardList) {
		try {
			this.getPrinter().printFile(destPath, cardList);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	

	

}
