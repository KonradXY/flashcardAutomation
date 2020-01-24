package main.java.engines.textengines;
import main.java.engines.TextEngine;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.evernote.EvernoteHtmlParser;
import main.java.strategy.ReadingFormatStrategy;
import main.java.utils.ParserUtil;

public class EvernoteEngine extends TextEngine {
	
	public EvernoteEngine() { super(); }
	public EvernoteEngine(String input, String output) { super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.NO_FORMAT);
		parser = new EvernoteHtmlParser(new ParserUtil(), getFullOutputPath());
		printer = new TextFilePrinter();
	}
}
