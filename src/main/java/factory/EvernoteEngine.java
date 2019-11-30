package main.java.factory;

import main.java.model.TextFileReader;
import main.java.model.evernote.EvernoteHtmlParser;
import main.java.model.simplemodel.TextFileWriter;
import main.java.strategy.FormatStrategy;

public class EvernoteEngine extends AbstractAnkiEngine {
	
	public EvernoteEngine() { super(); }
	public EvernoteEngine(String input, String output) { super(input, output); }
	
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(FormatStrategy.NO_FORMAT);
		parser = new EvernoteHtmlParser();
		printer = new TextFileWriter();
	}
}
