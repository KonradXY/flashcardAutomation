package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.parsers.DefaultParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.strategy.PrinterStrategy;
import main.java.strategy.ReadingFormatStrategy;

public class DefaultAnkiEngine extends TextEngine {
	
	public DefaultAnkiEngine() {super();}
	public DefaultAnkiEngine(String input, String output) {super(input,output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.ADD_PIPE);
		parser = new DefaultParser();
		printer = new TextFilePrinter(PrinterStrategy.NO_STRATEGY);
	}
	
}
