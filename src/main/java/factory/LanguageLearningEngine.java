package main.java.factory;

import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;

import main.java.baseModel.SimpleReader;
import main.java.languageLearningModel.LanguageLearningParser;
import main.java.languageLearningModel.LanguageLearningPrinter;
import main.java.strategy.FormatStrategy;

public class LanguageLearningEngine extends AbstractAnkiEngine {
	@Override
	public void buildEngine() {
		inputContent   += LANGUAGE_LEARNING_DIR;
		outputContent  += LANGUAGE_LEARNING_DIR;
		
		reader = new SimpleReader(FormatStrategy.ADD_NEW_LINE);
		parser = new LanguageLearningParser();
		printer = new LanguageLearningPrinter();
	}
}
