package main.java.enginefactory;

import static main.java.utils.Property.EVERNOTE_DIR;
import static main.java.utils.Property.GENERIC_DIR;
import static main.java.utils.Property.KINDLE_DIR;
import static main.java.utils.Property.LANGUAGE_LEARNING_DIR;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

// TODO - questo e' da rifare con guice

@Singleton
public class TextEngineBuilder {
	
	private static final Logger log = Logger.getLogger(TextEngineBuilder.class);
	
	public AbstractAnkiEngine createTextEngine(String[] inputParam) {

		if (inputParam == null) throw new RuntimeException("Inserire parametro !");
		
		AbstractAnkiEngine ankiModel;
		String input = inputParam[0];
		switch(input) {
			case "evernote": 			ankiModel = new EvernoteEngine(EVERNOTE_DIR, EVERNOTE_DIR + "evernoteParsed.txt"); break;
			case "kindle": 	 			ankiModel = new KindleEngine(KINDLE_DIR, KINDLE_DIR);   break;
			case "languageLearning": 	ankiModel = new LanguageLearningEngine(LANGUAGE_LEARNING_DIR, LANGUAGE_LEARNING_DIR); break;
			default : 					ankiModel = new DefaultEngine(GENERIC_DIR, GENERIC_DIR); break;
		}
		
		ankiModel.buildEngine();

		log.info("Cartella di input: " + ankiModel.getInputDestination());
		log.info("Cartella di output: " + ankiModel.getOutputDestination());
		
		return ankiModel;
	}
	
}






