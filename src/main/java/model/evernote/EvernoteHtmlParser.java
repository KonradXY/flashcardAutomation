package main.java.model.evernote;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiCard;
import main.java.modelDecorator.DecoratingCard;
import main.java.utils.ParserUtil;
import main.java.utils.Property;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.java.modelDecorator.StandardFormatCardDecorator.decorateWithLeftFormat;

public class EvernoteHtmlParser implements IParser {

    private static final int MAX_SIZE_CARD = 131072;
    private static final Logger log = Logger.getLogger(EvernoteHtmlParser.class);

    private ParserUtil parserUtil;
    
    public EvernoteHtmlParser() { }

    public EvernoteHtmlParser(ParserUtil parserUtil, Path imgInputContent) {
        this.parserUtil = parserUtil;
        this.parserUtil.setImgInputContent(imgInputContent);
    }
    
    @Override
    public List<IAnkiCard> parse(Map<Path, String> input) {
        List<IAnkiCard> cardList = new ArrayList<>();
        for (Map.Entry<Path, String> entry : input.entrySet())
            cardList.addAll(parseEvernoteFlashCards(entry.getKey(), entry.getValue()));

        return cardList;
    }

    private List<IAnkiCard> parseEvernoteFlashCards(Path fileName, String htmlContent) {
        Document htmlDoc = Jsoup.parse(htmlContent);
        formatImageTags(fileName, htmlDoc);
        
        List<IAnkiCard> cards = htmlDoc.getElementsByTag("tbody").stream()
							        .map(tbody -> parseCardFromTBody(tbody))
							        .filter(card -> !cardExceedMaxSize(card))
							        .map(DecoratingCard::decorateWithLeftFormat)
							        .collect(Collectors.toList());
        
        return cards;
    }

    private IAnkiCard parseCardFromTBody(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        Elements frontElements = content.get(0).getElementsByTag("div");
        Elements backElements = content.get(1).getElementsByTag("div");
        return new AnkiCard(frontElements, backElements);
    }
    
    // FIXME - esiste una size massima per le flashcard. Vedere una soluzione
    private boolean cardExceedMaxSize(IAnkiCard card) {
    	boolean check = card.getBack().text().length() > MAX_SIZE_CARD;
    	if (check) log.info("Card exceded max size ! ");
    	return check;
    }

    public void formatImageTags(Path fileName, Document doc) {
        String imgDir = parserUtil.getImageDir(fileName);	// TODO - qua sto pezzo puo' essere rifattorizzato in modo che stia dentro al parser util tranquillamente
        Path currDir = Paths.get(imgDir).getParent();
        parserUtil.setImagesForFlashcard(doc, currDir, fileName);
    }




}
