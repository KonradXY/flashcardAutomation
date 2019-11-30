package main.java.factory;

import static main.java.utils.Property.GENERIC_DIR;

import main.java.baseModel.SimpleParser;
import main.java.baseModel.SimplePrinter;
import main.java.baseModel.SimpleReader;
import main.java.strategy.FormatStrategy;

public class DefaultEngine extends AbstractAnkiEngine {
	
	@Override
	public void buildEngine() {
		inputContent   += GENERIC_DIR;
		outputContent  += GENERIC_DIR;
		
		reader = new SimpleReader(FormatStrategy.ADD_PIPE);
		parser = new SimpleParser();
		printer = new SimplePrinter();
	}
	
}
