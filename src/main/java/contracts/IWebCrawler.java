package main.java.contracts;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.webscraper.AbstractWebScraper.LOG_COUNTER;
import static main.java.webscraper.AbstractWebScraper.MAX_NUM_EXAMPLES_PER_WORD;

public interface IWebCrawler {

    // TODO - capire quale sia la firma migliore per quest'interfacia (vorrei che fosse identica a quella del text engine ... verificare).
    // TODO - se devo implementare il printer e' normale che il parser debba tornare delle

    Logger log = Logger.getLogger(IWebCrawler.class);

    default void writeCards(List<IAnkiCard> cards, BufferedWriter bos) throws IOException {
        for (IAnkiCard card : cards) {
            writeCard(card, bos);
        }

        bos.flush();
    }

    default void writeCard(IAnkiCard card, BufferedWriter bos) throws IOException {
        if (!card.getFront().text().trim().isEmpty()) {
            bos.write(card.toString());
            bos.flush();
        }

    }

    default List<String> getWordListFromFile(String inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
        List<String> wordsList = br.lines()
                .map(String::trim)
                //.limit(5)
                .collect(Collectors.toList());
        br.close();
        return wordsList;
    }


    default void logNumberOfWords(int number) {
        if (number % LOG_COUNTER == 0) {
            log.info("Numero di esempi parsati: " + number * MAX_NUM_EXAMPLES_PER_WORD);
        }
    }
}
