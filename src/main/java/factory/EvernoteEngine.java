package main.java.factory;

import static main.java.utils.Property.EVERNOTE_DIR;

import main.java.model.evernoteModel.EvernoteHtmlParser;
import main.java.model.simplemodel.SimplePrinter;
import main.java.model.simplemodel.SimpleReader;
import main.java.strategy.FormatStrategy;

public class EvernoteEngine extends AbstractAnkiEngine {
	
	@Override
	public void buildEngine() {
		inputContent  += EVERNOTE_DIR;
		outputContent += EVERNOTE_DIR + "evernoteParsed.txt";
		
		reader = new SimpleReader(FormatStrategy.NO_FORMAT);
		parser = new EvernoteHtmlParser();
		printer = new SimplePrinter();
	}
}
