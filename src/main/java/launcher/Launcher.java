package main.java.launcher;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.java.config.AnkiApplicationModule;
import main.java.facade.TextFileFacade;

public class Launcher {

    private final static Logger log = Logger.getLogger(Launcher.class);

    private static TextFileFacade textFileFacade;

    /**
     * @param args I valori che i parametri possono assumere sono i seguenti:
     *             <p>
     *             null - campo vuoto - "default"  <<<===  default engine
     *             evernote                        <<<=== evernote engine
     *             kindle                          <<<=== kindle engine
     *             languageLearning                <<<=== languageLearning engine
     *             generateIndex					<<<=== generate index engine
     *             webcrawler general              <<<=== spanish general flashcard (spanishWebCrawlerService)
     *             wencrawler simple               <<<=== spanish simple flashcard (just definition x2 ita/esp && esp&ita)
     *             webcrawler cloze                <<<=== spanish cloze flashcard
     */

    // NB: per aggiungere dell'html all'interno di un element devo usare il metodo append ! (almeno cosi' sembra dalle prove che sto facendo)
    public static void main(String[] args) {
            long timeSpent = System.currentTimeMillis();
            log.info("... Inizio creazione flashcard ...");

            Injector injector = Guice.createInjector(new AnkiApplicationModule());
            textFileFacade = injector.getInstance(TextFileFacade.class);

            if (args.length == 0) args = new String[]{"default"};
            List<String> inputArg = Arrays.asList(args);

            log.info(" ====>>> launching parsing from file ");
            textFileFacade.buildFlashcardsFromTextFile(inputArg);

            log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis() - timeSpent) / 1000 + " sec");
    }


}
