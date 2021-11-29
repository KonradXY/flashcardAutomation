package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.parsers.LanguageLearningParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.printers.PrintStrategy;
import main.java.model.readers.ReadStrategy;

public class LanguageLearningEngine extends TextEngine {

	public LanguageLearningEngine(String input, String output) {super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadStrategy.ADD_NEW_LINE);
		parser = new LanguageLearningParser();
		printer = new TextFilePrinter();
	}
}
