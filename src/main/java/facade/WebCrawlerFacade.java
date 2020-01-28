package main.java.facade;

import com.google.inject.Inject;
import main.java.engines.WebCrawlerEngine;
import main.java.engines.factories.WebCrawlerEngineFactory;
import main.java.netutilities.CertificateManager;
import org.apache.log4j.Logger;

import java.util.List;

public class WebCrawlerFacade {

    private static final Logger log = Logger.getLogger(WebCrawlerFacade.class);

    private final WebCrawlerEngineFactory engineFactory;

    @Inject
    WebCrawlerFacade(WebCrawlerEngineFactory engineFactory) {
        this.engineFactory = engineFactory;
    }

    public void createFlashcardsScrapingTheWeb(List<String> args) {

        try {
            CertificateManager.doTrustToCertificates();

            WebCrawlerEngine webCrawlerEngine = engineFactory.createEngine(args);

            //webCrawlerEngine.createFlashcards();    // TODO <<--- fixare (in piu' la roba qua sotto deve andare proprio dentro questo metodo)

            // TODO - sistemare il discorso input/output:

            // 1. fare lettura di cartella o file
            // 2. la scrittura su file deve avvenire con lo stesso nome dell'input pero' con sopra _scraped_list.txt
            // 3. vorrei capire se sia possibile effettuare la scrittura su file durante il parsing in maniera unificata  (il ciclo for dovrei farlo fuori dalla lista)
            // 4. prendendo l'idea di sopra dovrei vedere se riesco a fare come con l'anki engine (qualcosa che metta in piedi lettura dall'input, parsing e altra roba tutta insieme)
            //  (questi if qua sotto andranno a diventare uno strategy o qualcosa di simile al parser)

            // TODO - fatto questo vorrei fare in modo che la scrittura e la lettura siano completamente unificate

            // TODO - finito sto pezzo qua passare ai decorator e sistemarli una volta per tutte


        } catch (Exception ex) {
            log.error("Errore nella creazione di flashcard tramite webcrawler: " + ex);
            throw new RuntimeException(ex);
        }


        // TODO - PER IL DISCORSO STREAM2FILE:
        /*
        56

Probably the shortest way is to use Files.write along with the trick which converts the Stream to the Iterable:

Files.write(Paths.get(filePath), (Iterable<String>)stream::iterator);
For example:

Files.write(Paths.get("/tmp/numbers.txt"),
     (Iterable<String>)IntStream.range(0, 5000).mapToObj(String::valueOf)::iterator);
If it looks too hackish, use more explicit approach:

try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                     Paths.get("/tmp/numbers.txt")))) {
    IntStream.range(0, 5000).mapToObj(String::valueOf).forEach(pw::println);
}
         */

    }


}
