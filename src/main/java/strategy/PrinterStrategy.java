package main.java.strategy;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiDeck;
import main.java.model.kindle.KindleAnkiCard;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public enum PrinterStrategy {

    NO_STRATEGY {
        @Override public Path createNameOutputFile(Path outputFile) { return  outputFile; }

        @Override public void printCards(AnkiDeck deck) {
            try {
                Files.write(deck.getPathDest(), (Iterable<String>) deck.getCards().stream().map(it -> it.toString())::iterator);
            } catch (IOException ex) {
                System.out.println("Errore nella scrittura del file: " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    },

    KINDLE_STRATEGY {
        @Override public Path createNameOutputFile(Path outputFile) { return createFileName(outputFile.toString()); }


        @Override
        public void printCards(AnkiDeck deck) {
            int cardIndex = 0;
            List<KindleAnkiCard> kindleCards = deck.getCards().stream().map(it -> (KindleAnkiCard)it).collect(Collectors.toList());

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(deck.getDestFolder()), "UTF-8"))) {
                for (KindleAnkiCard kindleCard : kindleCards) {
                    bw.write(cardIndex++ + ". " + kindleCard.getTitle() + "|");
                    bw.write(kindleCard.getBack() + "\n\n");
                }
            } catch (IOException ex) {
                log.error("Errore nella scrittura del file: " + deck.getDestFolder(), ex);
            }
        }

    };

    public abstract Path createNameOutputFile(Path outputFile);
    public abstract void  printCards(AnkiDeck cards);

    private final static Logger log = Logger.getLogger(PrinterStrategy.class);


    private static Path createFileName(String filePath) {
        // log.info("filePath: " + filePath);

        String fileDir = getFileDir(filePath);

        String lastName = getFileName(filePath);
        // log.info("getFileName: " + lastName);

        lastName = removeSpecialChars(lastName);
        // log.info("remove special chars: " + lastName);

        lastName = truncateLongNames(lastName);
        // log.info("truncate long filenames: " + lastName);

        return Paths.get(fileDir+"/"+lastName);
    }

    private static String getFileName(String fileName) {
        if (fileName.lastIndexOf("/") != -1)
            return fileName.substring(fileName.lastIndexOf("/")+1);

        return fileName;
    }
    private static String getFileDir(String fileName) {
        return fileName.substring(0,fileName.lastIndexOf("/"));
    }

    private static String removeSpecialChars(String fileName) {
        return fileName
                .replace(".", "")
                .replace(":", " ")
                .replace("*", "")
                .replace("/", "_")
                .replace("txt", "")
                .trim()
                .concat(".txt");
    }

    private static String truncateLongNames(String fileName) {
        if (fileName.replace(".txt", "").length() > 50) {
            return fileName
                    .replace(".txt", "")
                    .substring(0,46).concat(".txt");
        }

        return fileName;
    }
}
