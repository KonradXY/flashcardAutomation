package main.java.engines.factories;

import static main.java.utils.Property.EVERNOTE_PATH;
import static main.java.utils.Property.GENERIC_PATH;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.KINDLE_PATH;
import static main.java.utils.Property.LANGUAGE_LEARNING_PATH;
import static main.java.utils.Property.OUTPUT_DIR;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

import main.java.engines.AnkiEngine;
import main.java.engines.TextEngine;
import main.java.engines.textengines.DefaultAnkiEngine;
import main.java.engines.textengines.EvernoteEngine;
import main.java.engines.textengines.GenerateIndexEngine;
import main.java.engines.textengines.KindleEngine;
import main.java.engines.textengines.LanguageLearningEngine;

@Singleton
public class TextEngineFactory extends AbstractEngineFactory {
	
	private static final Logger log = Logger.getLogger(TextEngineFactory.class);

	@Override
	public AnkiEngine createEngine(List<String> inputParam) {

		if (inputParam == null) throw new RuntimeException("Inserire parametro !");

		TextEngine textEngine;

		String input = inputParam.get(0);
		switch(input) {
			case "evernote": 			textEngine = new EvernoteEngine(INPUT_DIR + EVERNOTE_PATH, OUTPUT_DIR + EVERNOTE_PATH); break;
			case "kindle": 	 			textEngine = new KindleEngine(INPUT_DIR + KINDLE_PATH, OUTPUT_DIR + KINDLE_PATH);   break;
			case "languageLearning": 	textEngine = new LanguageLearningEngine(INPUT_DIR + LANGUAGE_LEARNING_PATH, OUTPUT_DIR +  LANGUAGE_LEARNING_PATH); break;
			case "generateIndex":		textEngine = new GenerateIndexEngine(INPUT_DIR + GENERIC_PATH, OUTPUT_DIR + GENERIC_PATH); break;
			default : 					textEngine = new DefaultAnkiEngine(INPUT_DIR + GENERIC_PATH, OUTPUT_DIR + GENERIC_PATH); break;
		}
		
		textEngine.buildEngine();

		log.info("Cartella di input: " + textEngine.getInputDir());
		log.info("Cartella di output: " + textEngine.getOutputDir());
		
		return textEngine;
	}

}






