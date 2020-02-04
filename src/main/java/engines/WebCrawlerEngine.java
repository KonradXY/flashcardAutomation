
package main.java.engines;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.contracts.IWebCrawler;
import main.java.model.readers.TextListFileReader;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.webscraper.AbstractWebScraper.*;

public abstract class WebCrawlerEngine extends AbstractEngine {

    private final static Logger log = Logger.getLogger(WebCrawlerEngine.class);

    protected IReader reader;
    protected IWebCrawler webCrawler;
    protected IPrinter printer;

    @Override
    public void createFlashcards()  {

        // TODO - splittare la funzione in 3 parti come fatto per il textEngine !

        Map<Path, List<String>> contentMap = readFile(getFullInputPath());

        int numWords = 0;

        for (Map.Entry<Path, List<String>> entry : contentMap.entrySet()) {

            try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getOutputFileName(entry.getKey())), "UTF-8"))) {

                for (String word : entry.getValue()) {
                    List<IAnkiCard> cardList = webCrawler.createFlashcards(word);
                    writeCards(cardList, bos);

                    logNumberOfWords(numWords++);
                    Thread.sleep(TIME_SLEEP);
                }

                log.info("effettuata la creazione di " + numWords + " carte");

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

    }


    // ************ reading functions
    private Map<Path, List<String>> readFile(Path input) {
        try {
            return reader.readFile(input);
        } catch (IOException ex) {
            log.error("Errore nella lettura del file: " + input, ex);
            throw new RuntimeException(ex);
        }
    }

    // ************ writing functions
    public String getOutputFileName(Path inputFileName) throws IOException {
        String outputFile = inputFileName.toString().replace("input","output");
        outputFile = outputFile.substring(0, outputFile.lastIndexOf(".")).concat("_scraped.txt");

        if (!Files.exists(Paths.get(outputFile))) {
            Files.createFile(Paths.get(outputFile));
        }

        log.info("Scrittura sul file: " + outputFile);
        return outputFile;
    }

    public void writeCards(List<IAnkiCard> cards, BufferedWriter bos) throws IOException {
        for (IAnkiCard card : cards) {
            writeCard(card, bos);
        }

        bos.flush();
    }

    public void writeCard(IAnkiCard card, BufferedWriter bos) throws IOException {
        if (!card.getFront().text().trim().isEmpty()) {
            bos.write(card.toString());
            bos.flush();
        }
    }

    // ************* other functions
    private void logNumberOfWords(int number) {
        if (number % LOG_COUNTER == 0) {
            log.info("Numero di esempi parsati: " + number * MAX_NUM_EXAMPLES_PER_WORD);
        }
    }




    // ======= GETTERs & SETTERs
    public IWebCrawler getWebCrawler() { return webCrawler; }

    public void setWebCrawler(IWebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

}
