package main.java.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import main.java.config.AnkiApplicationModule;
import main.java.service.SpanishWebCrawlerService;
import main.java.facade.TextFileFacade;
import main.java.facade.WebCrawlerFacade;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static main.java.utils.Property.WEB_CRAWLER_DIR;
import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

public class Launcher {

    private final static Logger log = Logger.getLogger(Launcher.class);

    private static TextFileFacade textFileFacade;
    private static SpanishWebCrawlerService spanishWebCrawlerService;
    private static WebCrawlerFacade webCrawlerFacade;

    /**
     * @param args
     *
     * I valori che i parametri possono assumere sono i seguenti:
     *
     *          null - campo vuoto - "default"  <<<===  default engine
     *          evernote                        <<<=== evernote engine
     *          kindle                          <<<=== kindle engine
     *          languageLearning                <<<=== languageLearning engine
     *          webcrawler general              <<<=== spanish general flashcard (spanishWebCrawlerService)
     *          wencrawler simple               <<<=== spanish simple flashcard (just definition x2 ita/esp && esp&ita)
     *          webcrawler cloze                <<<=== spanish cloze flashcard
     */


    public static void main(String[] args) {
        try {

        	long timeSpent = System.currentTimeMillis();
            log.info("... Inizio creazione flashcard ...");

            Injector injector = Guice.createInjector(new AnkiApplicationModule());
            textFileFacade = injector.getInstance(TextFileFacade.class);
            spanishWebCrawlerService = injector.getInstance(SpanishWebCrawlerService.class);
            webCrawlerFacade = injector.getInstance(WebCrawlerFacade.class);

            if (args.length == 0) args = new String[]{"default"};
            List<String> inputArg = Arrays.asList(args);



            if (inputArg.contains("webcrawler")) {
                log.info(" ====>>> launching webcrawling mode");
                webCrawlerFacade.createFlashcardsScrapingTheWeb(inputArg);
            }
            else {
                log.info(" ====>>> launching parsing from file ");
                textFileFacade.buildFlashcardsFromTextFile(inputArg);
            }

            log.info("Creazione flashcard completata ! - Tempo impiegato: " + (System.currentTimeMillis() - timeSpent) / 1000 + " sec");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }




}
