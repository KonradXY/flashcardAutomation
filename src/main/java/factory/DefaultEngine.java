package main.java.factory;

import main.java.model.TextFileReader;
import main.java.model.simplemodel.SimpleParser;
import main.java.model.simplemodel.TextFileWriter;
import main.java.strategy.FormatStrategy;

public class DefaultEngine extends AbstractAnkiEngine {
	
	public DefaultEngine() {super();}
	public DefaultEngine(String input, String output) {super(input,output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(FormatStrategy.ADD_PIPE);
		parser = new SimpleParser();
		printer = new TextFileWriter();
	}
	
}
