package main.java.factory;

import static main.java.utils.Property.EVERNOTE_DIR;

import main.java.baseModel.SimplePrinter;
import main.java.baseModel.SimpleReader;
import main.java.evernoteModel.EvernoteHtmlParser;
import main.java.strategy.ReadingStrategy;

public class EvernoteEngine extends AbstractAnkiEngine {
	
	@Override
	public void buildEngine() {
		inputContent  += EVERNOTE_DIR;
		outputContent += EVERNOTE_DIR + "evernoteParsed.txt";
		
		reader = new SimpleReader(ReadingStrategy.EVERNOTE);
		parser = new EvernoteHtmlParser();
		printer = new SimplePrinter();
	}
}