package main.java.main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import main.java.engines.AnkiEngine;
import main.java.engines.factories.TextEngineFactory;
import main.java.facade.TextFileFacade;
import org.apache.log4j.Logger;

import java.util.List;

@Singleton
class Launcher {

    private final static Logger log = Logger.getLogger(Main.class);

    private final TextEngineFactory textEngineFactory;

    @Inject
    public Launcher(TextEngineFactory textEngineFactory) {
        this.textEngineFactory = textEngineFactory;
    }

    public void buildFlashcards(List<String> args) {
        AnkiEngine ankiEngine = textEngineFactory.createEngine(args);
        createFlashcards(ankiEngine);

    }

    private void createFlashcards(AnkiEngine ankiEngine) {
        long timeSpent = System.currentTimeMillis();
        log.info("... Inizio creazione flashcard ...");

        ankiEngine.createFlashcards();
        log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis() - timeSpent) / 1000 + " sec");
    }
}