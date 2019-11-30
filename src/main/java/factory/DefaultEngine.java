package main.java.factory;

import static main.java.utils.Property.GENERIC_DIR;

import main.java.model.simplemodel.SimpleParser;
import main.java.model.simplemodel.SimplePrinter;
import main.java.model.simplemodel.SimpleReader;
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
