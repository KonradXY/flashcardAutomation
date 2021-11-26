package main.java.model.parsers;

import main.java.model.card.AnkiCard;
import main.java.model.card.IAnkiCard;
import main.java.model.card.card_decorators.LeftFormatDecorator;
import main.java.model.deck.AnkiDeck;
import main.java.utils.EvernoteImageParser;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EvernoteParser implements IParser {

    private static final int MAX_SIZE_CARD = 131072;
    private static final Logger log = Logger.getLogger(EvernoteParser.class);

    private final EvernoteImageParser evernoteImageParser;

    public EvernoteParser() {
        this.evernoteImageParser = new EvernoteImageParser();
    }


    @Override
    public List<AnkiDeck> parse(Path fileName, String input, String destFolder) {
        AnkiDeck deck = new AnkiDeck.Builder()
                .withDestFolder(destFolder)
                .withTitle(getParsedFileName(fileName))
                .withCards(parseFlashCards(fileName, input, Paths.get(destFolder)))
                .build();
        return Collections.singletonList(deck);
    }

    private List<IAnkiCard> parseFlashCards(Path fileName, String htmlContent, Path outputContent) {
        Document htmlDoc = Jsoup.parse(htmlContent);
        createImagesForFlashCards(fileName, outputContent, htmlDoc);
        return htmlDoc.getElementsByTag("tbody").stream()
                .map(this::parseCard)
                .filter(this::doesNotExceedMaxSize)
                .map(LeftFormatDecorator::decorateWithLeftFormat)
                .collect(Collectors.toList());
    }

    private IAnkiCard parseCard(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        Elements frontElements = content.get(0).getElementsByTag("div");
        Elements backElements = content.get(1).getElementsByTag("div");
        return new AnkiCard(frontElements, backElements);
    }

    private boolean doesNotExceedMaxSize(IAnkiCard card) {
        boolean check = card.getBack().text().length() <= MAX_SIZE_CARD && card.getFront().text().length() <= MAX_SIZE_CARD;
        if (!check) {
            log.info("Card exceded max size ! Card title: " + card.getFront().text());
        }
        return check;
    }

    private void createImagesForFlashCards(Path fileName, Path outputContent, Document htmlDoc) {
        try {
            evernoteImageParser.parseImages(htmlDoc, outputContent, fileName);
        } catch (IOException ex) {
            log.error("Errore nella copia delle immagini nel media collector.", ex);
        }
    }


}
