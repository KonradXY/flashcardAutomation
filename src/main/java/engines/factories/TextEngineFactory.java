package main.java.engines.factories;

import static main.java.utils.Property.EVERNOTE_PATH;
import static main.java.utils.Property.GENERIC_PATH;
import static main.java.utils.Property.KINDLE_PATH;
import static main.java.utils.Property.LANGUAGE_LEARNING_PATH;

import main.java.engines.*;
import org.apache.log4j.Logger;

import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class TextEngineFactory extends AbstractEngineFactory {
	
	private static final Logger log = Logger.getLogger(TextEngineFactory.class);

	@Override
	public AbstractEngine createEngine(List<String> inputParam) {

		if (inputParam == null) throw new RuntimeException("Inserire parametro !");

		TextEngine textEngine;

		String input = inputParam.get(0);
		switch(input) {
			case "evernote": 			textEngine = new EvernoteEngine(EVERNOTE_PATH, EVERNOTE_PATH); break;
			case "kindle": 	 			textEngine = new KindleEngine(KINDLE_PATH, KINDLE_PATH);   break;
			case "languageLearning": 	textEngine = new LanguageLearningEngine(LANGUAGE_LEARNING_PATH, LANGUAGE_LEARNING_PATH); break;
			default : 					textEngine = new DefaultAnkiEngine(GENERIC_PATH, GENERIC_PATH); break;
		}
		
		textEngine.buildEngine();

		log.info("Cartella di input: " + textEngine.getFullInputDir());
		log.info("Cartella di output: " + textEngine.getFullOutputDir());
		
		return textEngine;
	}

}






