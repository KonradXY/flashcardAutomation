package main.java.factory;

import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;

import main.java.baseModel.SimpleReader;
import main.java.languageLearningModel.LanguageLearningParser;
import main.java.languageLearningModel.LanguageLearningPrinter;
import main.java.strategy.ReadingStrategy;

public class LanguageLearningEngine extends AbstractAnkiEngine {
	@Override
	public void buildEngine() {
		inputContent   += LANGUAGE_LEARNING_DIR;
		outputContent  += LANGUAGE_LEARNING_DIR;
		
		reader = new SimpleReader(ReadingStrategy.LANGUAGE_LEARNING);
		parser = new LanguageLearningParser();
		printer = new LanguageLearningPrinter();
	}
}
