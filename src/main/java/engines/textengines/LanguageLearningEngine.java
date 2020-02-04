package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.languageLearning.LanguageLearningParser;
import main.java.strategy.ReadingFormatStrategy;

public class LanguageLearningEngine extends TextEngine {
	
	public LanguageLearningEngine() { super(); }
	public LanguageLearningEngine(String input, String output) {super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.ADD_NEW_LINE);
		parser = new LanguageLearningParser();
		printer = new TextFilePrinter();
	}
}
