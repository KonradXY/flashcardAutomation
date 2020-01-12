package main.java.enginefactory;

import main.java.model.TextFilePrinter;
import main.java.model.TextFileReader;
import main.java.model.languageLearning.LanguageLearningParser;
import main.java.strategy.ReadingFormatStrategy;

public class LanguageLearningAnkiEngine extends AbstractAnkiEngine {
	
	public LanguageLearningAnkiEngine() { super(); }
	public LanguageLearningAnkiEngine(String input, String output) {super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(ReadingFormatStrategy.ADD_NEW_LINE);
		parser = new LanguageLearningParser();
		printer = new TextFilePrinter();
	}
}
