
package main.java.engines;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.contracts.IWebCrawler;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.java.webscraper.AbstractWebScraper.*;

public abstract class WebCrawlerEngine extends AbstractEngine {

    private final static Logger log = Logger.getLogger(WebCrawlerEngine.class);

    protected IWebCrawler webCrawler;

    @Override
    public void createFlashcards() {

        int numWords = 0;

        try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFullOutputDir()), "UTF-8"))) {
            List<String> wordList = getWordListFromFile(getFullInputDir());

            for (String word : wordList) {
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

    // *********** reading functions
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
