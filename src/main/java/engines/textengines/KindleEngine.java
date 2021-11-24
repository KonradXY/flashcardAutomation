package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.parsers.KindleParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.printers.PrinterStrategy;
import main.java.model.readers.ReadStrategy;

public class KindleEngine extends TextEngine {

	public KindleEngine(String input, String output) { super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadStrategy.REPLACE_NEW_LINES);
		parser = new KindleParser();
		printer = new TextFilePrinter(PrinterStrategy.KINDLE_STRATEGY);
	}
}
