package main.java.factory;

import static main.java.utils.Property.*;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

// TODO - questo e' da rifare con guice

@Singleton
public class EngineBuilder {
	
	private static final Logger log = Logger.getLogger(EngineBuilder.class);
	
	public AbstractAnkiEngine createTextEngine(String[] inputParam) {

		if (inputParam == null) throw new RuntimeException("Inserire parametro !");
		
		AbstractAnkiEngine ankiModel;
		String input = inputParam[0];
		switch(input) {
			case "evernote": 			ankiModel = new EvernoteEngine(EVERNOTE_DIR, EVERNOTE_DIR + "evernoteParsed.txt"); break;
			case "kindle": 	 			ankiModel = new KindleEngine(KINDLE_DIR, KINDLE_DIR);   break;
			case "languageLearning": 	ankiModel = new LanguageLearningEngine(LANGUAGE_LEARNING_DIR, LANGUAGE_LEARNING_DIR); break;
			// TODO - introdurre web e cloze come modelli
			default : 					ankiModel = new DefaultEngine(GENERIC_DIR, GENERIC_DIR); break;
		}
		
		ankiModel.buildEngine();

		log.info("Cartella di input: " + ankiModel.getInputContent());
		log.info("Cartella di output: " + ankiModel.getOutputContent());
		
		return ankiModel;
	}
	
}






