package main.java.enginefactory;

import main.java.model.DefaultParser;
import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.strategy.ReadingFormatStrategy;

public class DefaultEngine extends AbstractAnkiEngine {
	
	public DefaultEngine() {super();}
	public DefaultEngine(String input, String output) {super(input,output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.ADD_PIPE);
		parser = new DefaultParser();
		printer = new TextFilePrinter();
	}
	
}
