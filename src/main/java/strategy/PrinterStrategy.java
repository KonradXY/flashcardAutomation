package main.java.strategy;

import main.java.contracts.IAnkiCard;
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
        @Override public void sortCards(List<IAnkiCard> cards) { }

        @Override public void printCards(Path destPath, List<IAnkiCard> cards) {
            try {
                Files.write(destPath, (Iterable<String>) cards.stream().map(it -> it.toString())::iterator);
            } catch (IOException ex) {
                System.out.println("Errore nella scrittura del file: " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    },

    KINDLE_STRATEGY {
        @Override public Path createNameOutputFile(Path outputFile) { return createFileName(outputFile.toString()); }
        @Override public void sortCards(List<IAnkiCard> cards) { cards.stream().map(it -> (KindleAnkiCard) it).sorted().collect(Collectors.groupingBy(KindleAnkiCard::getTitle)); }

        @Override
        public void printCards(Path destPath, List<IAnkiCard> cards) {
            int cardIndex = 0;
            List<KindleAnkiCard> kindleCards = cards.stream().map(it -> (KindleAnkiCard)it).collect(Collectors.toList());

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath.toString()), "UTF-8"))) {
                for (KindleAnkiCard kindleCard : kindleCards) {
                    bw.write(cardIndex++ + ". " + kindleCard.getTitle() + "|");
                    bw.write(kindleCard.getBack() + "\n\n");
                }
            } catch (IOException ex) {
                log.error("Errore nella scrittura del file: " + destPath, ex);
            }
        }

    };

    public abstract Path createNameOutputFile(Path outputFile);
    public abstract void sortCards(List<IAnkiCard> cards);
    public abstract void  printCards(Path destPath, List<IAnkiCard> cards);

    private final static Logger log = Logger.getLogger(PrinterStrategy.class);


    private static Path createFileName(String fileName) {
        if (fileName.lastIndexOf("\\") != -1)
            fileName = fileName.substring(fileName.lastIndexOf("\\"));

        fileName = fileName.replace(".", "")
                .replace(":", " ")
                .replace("*", "")
                .replace("/", "_")
                .trim()
                .concat(".txt")
        ;

        if (fileName.length() > 50) {
            fileName = fileName.substring(0,46).concat(".txt");
        }

        return Paths.get(fileName);
    }
}
