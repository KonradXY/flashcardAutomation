package main.java.factory;

import static main.java.utils.Property.GENERIC_DIR;

import main.java.baseModel.SimpleParser;
import main.java.baseModel.SimplePrinter;
import main.java.baseModel.SimpleReader;
import main.java.strategy.ReadingStrategy;

public class DefaultEngine extends AbstractAnkiEngine {
	
	@Override
	public void buildEngine() {
		inputContent   += GENERIC_DIR;
		outputContent  += GENERIC_DIR;
		
		reader = new SimpleReader(ReadingStrategy.GENERAL);
		parser = new SimpleParser();
		printer = new SimplePrinter();
	}
	
}
