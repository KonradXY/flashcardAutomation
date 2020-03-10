package main.java.model.evernote;

import main.java.card_decorators.StandardCardDecorator;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiCard;
import main.java.utils.ParserUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EvernoteHtmlParser implements IParser {

    private ParserUtil parserUtil;
    private Path outputContent;

    public EvernoteHtmlParser(ParserUtil parserUtil, Path outputContent) {
        this.parserUtil = parserUtil;
        this.outputContent = outputContent;
    }


    @Override
    public List<IAnkiCard> parse(Path filename, String input) {
        List<IAnkiCard> cardList = new ArrayList<>();
        cardList.addAll(parseEvernoteFlashCards(filename, input, outputContent));
        return cardList;
    }

    @Override
    public Map<Path, List<IAnkiCard>> sort(Map<Path, List<IAnkiCard>> mapContent) {
        return mapContent;
    }


    private List<IAnkiCard> parseEvernoteFlashCards(Path fileName, String htmlContent, Path outputContent) {
        Document htmlDoc = Jsoup.parse(htmlContent);

        parserUtil.createImagesForFlashcard(htmlDoc, outputContent, fileName);

        return htmlDoc.getElementsByTag("tbody").stream()
                .map(this::parseCardFromTBody)
                .filter(card -> !parserUtil.cardExceedMaxSize(card))
                .map(StandardCardDecorator::decorateWithLeftFormat)
                .collect(Collectors.toList());
    }

    private IAnkiCard parseCardFromTBody(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        Elements frontElements = content.get(0).getElementsByTag("div");
        Elements backElements = content.get(1).getElementsByTag("div");
        return new AnkiCard(frontElements, backElements);
    }


}
