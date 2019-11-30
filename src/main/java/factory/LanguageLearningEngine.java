package main.java.factory;

import main.java.model.TextFileReader;
import main.java.model.languageLearning.LanguageLearningParser;
import main.java.model.languageLearning.LanguageLearningPrinter;
import main.java.strategy.FormatStrategy;

public class LanguageLearningEngine extends AbstractAnkiEngine {
	
	public LanguageLearningEngine() { super(); }
	public LanguageLearningEngine(String input, String output) {super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(FormatStrategy.ADD_NEW_LINE);
		parser = new LanguageLearningParser();
		printer = new LanguageLearningPrinter();
	}
}
