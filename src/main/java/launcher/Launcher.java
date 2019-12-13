package main.java.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import main.java.config.AnkiApplicationModule;
import main.java.facade.FlashcardFacade;
import org.apache.log4j.Logger;

import static main.java.utils.Property.WEB_CRAWLER_DIR;

public class Launcher {

    private final static Logger log = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        try {

        	long timeSpent = System.currentTimeMillis();
            log.info("... Inizio creazione flashcard ...");

            Injector injector = Guice.createInjector(new AnkiApplicationModule());
            FlashcardFacade facade = injector.getInstance(FlashcardFacade.class);

            if (args.length == 0) args = new String[]{"default"};
            String inputArg = args[0].toLowerCase();

            // TODO - tutto sto pezzo deve andare all'interno dell'engineBuilder
            if (inputArg.contains("webcrawling")) {
                log.info(" ====>>> launching webcrawling mode");
                String inputDir = WEB_CRAWLER_DIR + "1k_lista2.txt";
                String outputDir = WEB_CRAWLER_DIR + "scrapedList.txt";
				facade.buildFlashcardsFromWeb(inputDir, outputDir);

            } else if (inputArg.contains("clozecrawling")) {
                // TODO - sta roba va a finire all'interno del build properties (che finisce all'interno del main praticamente -> in questo caso il mediator si pone all'interno del parsing ma dovrei studiarmela meglio come cosa
                log.info(" ====>>> launching clozeCrawling mode");
                String inputDir = WEB_CRAWLER_DIR + "1k_lista2.txt";
                String outputDir = WEB_CRAWLER_DIR + "scrapedList.txt";
				facade.buildClozeFlashcardsFromWeb(inputDir, outputDir);

            } else {
                log.info(" ====>>> launching parsing from file ");
                facade.buildFlashcardsFromTextFile(args);

            }

            log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis() - timeSpent) / 1000 + " sec");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }




}
