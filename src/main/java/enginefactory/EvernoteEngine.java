package main.java.enginefactory;

import java.nio.file.Paths;

import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.evernote.EvernoteHtmlParser;
import main.java.strategy.ReadingFormatStrategy;

public class EvernoteEngine extends AbstractAnkiEngine {
	
	public EvernoteEngine() { super(); }
	public EvernoteEngine(String input, String output) { super(input, output); }
	
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.NO_FORMAT);
		parser = new EvernoteHtmlParser(Paths.get(getOutputDestination()));
		printer = new TextFilePrinter();
	}
}
