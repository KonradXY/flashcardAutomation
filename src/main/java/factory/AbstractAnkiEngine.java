package main.java.factory;

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
import main.java.model.AnkiCard;

public abstract class AbstractAnkiEngine {
	
	protected IReader reader;
	protected IParser parser;
	protected IPrinter printer;
	
	protected String inputDir;
	protected String outputDir;
	
	protected String inputContent;
	protected String outputContent;

	protected AbstractAnkiEngine() {
		this.inputDir = INPUT_DIR;
		this.outputDir = OUTPUT_DIR;
	}
	
	protected AbstractAnkiEngine(String input, String output) {
		this();
		this.inputContent  = input;
		this.outputContent = output;
	}
	
	
	public abstract void buildEngine();
	
	public void createFlashcards() {
		Map<Path, String> contentRead = this.read();
		List<IAnkiCard> cardList = this.parse(contentRead);
		this.print(cardList);
	}
	
	
	public Map<Path, String> read() {
		return read(Paths.get(getInputDestination()));
	}
	public List<IAnkiCard> parse(Map<Path, String> content) {
		return this.getParser().parse(content);
	}
	public void print(List<IAnkiCard> cardList) {
		this.print(cardList, this.getOutputDestination());
	}
	

	
	public Map<Path, String> read(Path file) {
		try {
			return this.getReader().readFile(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void print(List<IAnkiCard> cardList, String destPath) {
		try {
			this.getPrinter().printFile(destPath, cardList);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public IReader getReader() 						{ return reader; }
	public IParser getParser() 						{ return parser; }
	public IPrinter getPrinter() 					{ return printer; }

	public void setReader(IReader reader) 			{ this.reader = reader; }
	public void setParser(IParser parser) 			{ this.parser = parser; }
	public void setPrinter(IPrinter printer) 		{ this.printer = printer; }
	
	public String getInputDestination()				{ return inputDir + inputContent; }
	public String getOutputDestination() 			{ return outputDir + outputContent; }
	public String getInputDir() 					{ return inputDir;}
	public String getOutputDir() 					{ return outputDir; }
	public String getInputContent()					{ return inputContent; }
	public String getOutputContent()				{ return outputContent; }
	
	public void setInputDir(String inputContent) 	{ this.inputDir = inputContent; }
	public void setOutputDir(String outputDir) 		{ this.outputDir = outputDir; }
	public void setInputContent(String content)		{ this.inputContent = content; }
	public void setOutputContent(String content) 	{ this.outputContent = content; }
}
