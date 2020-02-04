
package main.java.engines;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IReader;
import main.java.contracts.IWebCrawler;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static main.java.webscraper.AbstractWebScraper.*;

public abstract class WebCrawlerEngine extends AbstractEngine {

    private final static Logger log = Logger.getLogger(WebCrawlerEngine.class);

    protected IWebCrawler webCrawler;

    @Override
    public void createFlashcards() {

        // TODO - splittare la funzione in 3 parti come fatto per il textEngine !

        int numWords = 0;
        Map<Path, List<String>> contentMap = new HashMap<>();
        readContent(contentMap, getFullInputPath());

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

    // *********** reading functions

    private void readContent(Map<Path, List<String>> contentMap, Path filePath) {
        try {
            if (!Files.isDirectory(filePath)) {
                Stream.of(filePath)
                        .filter(IReader::isParseable)
                        .forEach(it -> addEntryToMap(contentMap, it));
                return;
            }

            Files.walk(filePath)
                    .filter(p -> !p.equals(filePath))
                    .sorted()
                    .forEach(path -> readContent(contentMap, path));

        } catch (IOException ex) {
            log.error("Errore nella lettura dei file: ", ex);
        }

    }

    private void addEntryToMap(Map<Path, List<String>> mapInput, Path pathFile) {
        if (!mapInput.containsKey(pathFile)) {
            log.info("lettura file: " + pathFile);
            try {
                mapInput.put(pathFile, getWordListFromFile(pathFile.toString()));
            } catch (IOException ex) {
                log.error("Errore durante la lettura del file: " + pathFile, ex);
            }
        }
    }

    public List<String> getWordListFromFile(String inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
        List<String> wordsList = br.lines()
                .map(String::trim)
                //.limit(5)
                .collect(Collectors.toList());
        br.close();
        return wordsList;
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
