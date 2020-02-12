package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.kindle.KindleParser;
import main.java.strategy.PrinterStrategy;
import main.java.strategy.ReadingFormatStrategy;

public class KindleEngine extends TextEngine {
	
	public KindleEngine() { super(); }
	public KindleEngine(String input, String output) { super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.REPLACE_NEW_LINES);
		parser = new KindleParser();
		printer = new TextFilePrinter(PrinterStrategy.KINDLE_STRATEGY);
	}
}
