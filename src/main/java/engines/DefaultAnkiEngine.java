package main.java.engines;

import main.java.model.DefaultParser;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.strategy.ReadingFormatStrategy;

public class DefaultAnkiEngine extends TextEngine {
	
	public DefaultAnkiEngine() {super();}
	public DefaultAnkiEngine(String input, String output) {super(input,output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.ADD_PIPE);
		parser = new DefaultParser();
		printer = new TextFilePrinter();
	}
	
}
