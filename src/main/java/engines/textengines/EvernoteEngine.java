package main.java.engines.textengines;
import main.java.engines.TextEngine;
import main.java.model.parsers.EvernoteParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;

public class EvernoteEngine extends TextEngine {

	public EvernoteEngine(String input, String output) { super(input, output); }

	@Override
	public void buildEngine() {
		reader = new TextFileReader();
		parser = new EvernoteParser();
		printer = new TextFilePrinter();
	}
}
