package main.java.engines.textengines;
import main.java.engines.TextEngine;
import main.java.model.parsers.EvernoteParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.printers.PrinterStrategy;
import main.java.model.readers.ReadingFormatStrategy;
import main.java.utils.ParserUtil;

public class EvernoteEngine extends TextEngine {

	public EvernoteEngine(String input, String output) { super(input, output); }

	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.NO_FORMAT);
		parser = new EvernoteParser(new ParserUtil());
		printer = new TextFilePrinter(PrinterStrategy.NO_STRATEGY);
	}
}
