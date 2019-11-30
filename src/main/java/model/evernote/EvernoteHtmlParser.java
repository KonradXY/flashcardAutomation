package main.java.model.evernote;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IParser;
import main.java.model.AbstractAnkiCard;
import main.java.utils.Property;

public class EvernoteHtmlParser implements IParser {

    private final static int MAX_SIZE_CARD = 131072;

    private static final Logger log = Logger.getLogger(EvernoteHtmlParser.class);

    private static final String MARGIN = "margin", AUTO = "auto";
    private static final String ALIGN = "align", LEFT = "left";
    private static final String TEXT_ALIGN = "text-align";
    private static final String FONT_STYLE = "font style";
    private static final String FONT_SIZE = "font-size: 10pt";

    @Override
    public List<AbstractAnkiCard> parse(Map<Path, String> input) {
        List<AbstractAnkiCard> cardList = new ArrayList<>();
        for (Map.Entry<Path, String> entry : input.entrySet())
            cardList.addAll(parseEvernoteCardTableFromFile(entry.getKey(), entry.getValue()));

        return cardList;
    }

    private List<AbstractAnkiCard> parseEvernoteCardTableFromFile(Path fileName, String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        formatImageTags(fileName, doc);
        return createCards(doc, fileName);
    }

    private List<AbstractAnkiCard> createCards(Document doc, Path fileName) {
        List<AbstractAnkiCard> cardList = new ArrayList<>();

        for (Element tbody : doc.getElementsByTag("tbody")) {
            AbstractAnkiCard card = parseCardFromTBody(tbody);

            // FIXME - esiste una size massima per le flashcard. Vedere una soluzione 
            if (card.getValue().text().length() > MAX_SIZE_CARD) {
                log.info("Card exceded max size ! ");
                continue;
            }

            cardList.add(card);
        }
        return cardList;
    }


    private AbstractAnkiCard parseCardFromTBody(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        String cardFront = formatRowContent(content.get(0));
        String cardBack = formatRowContent(content.get(1));
        return new EvernoteAnkiCard(cardFront, cardBack);
    }

    private String formatRowContent(Element elem) {
        for (Element div : elem.getElementsByTag("div"))
            formatNodeElement(div);

        return IParser.replaceNewLines(elem.toString());
    }

    private Element formatNodeElement(Element elem) {
        removeUselessAttrs(elem, "style");
        return elem.attr(ALIGN, LEFT)
                .attr(TEXT_ALIGN, LEFT)
                .attr(FONT_STYLE, FONT_SIZE)
                .attr(MARGIN, AUTO);
    }

    private void removeUselessAttrs(Element elem, String... attrs) {
        for (String attr : Arrays.asList(attrs)) 
        	elem.removeAttr(attr);
    }

    public void formatImageTags(Path fileName, Document doc) {
        String imgDir = getImageDir(fileName);
        Path currDir = Paths.get(imgDir).getParent();
        setImagesForFlashcard(doc, currDir, fileName);
    }

    private void setImagesForFlashcard(Document doc, Path currDir, Path fileName) {
        String imgTitle;
        Path dest;
        Path source;

        for (Element img : doc.getElementsByTag("img")) {
            imgTitle = img.attr("src");
            source = currDir.resolve(imgTitle);
            String titleImage = getTitleForImage(fileName, imgTitle);
            dest = Paths.get(Property.OUTPUT_DIR+Property.EVERNOTE_DIR).resolve(titleImage);
            copyFile(source, dest);
            img.attr("src", getTitleForImage(fileName, imgTitle));
        }
    }

    private void copyFile(Path src, Path dest) {
        if (!Files.exists(src))
            throw new RuntimeException("FILE SRC NON TROVATO: " + src);

        if (Files.isDirectory(dest)) {
            throw new RuntimeException("FILE DEST NON PUO' ESSERE DIRECTORY ! " + dest);
        }

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Error while copying file: " + ex);
        }

    }

    private String getTitleForImage(Path filePath, String imgName) {

        int first = filePath.toString().lastIndexOf(File.separatorChar)+1;
        int last = filePath.toString().lastIndexOf(".");
        String imgTitle = filePath.toString().substring(first, last);

        first = imgName.lastIndexOf("/") + 1;
        last = imgName.lastIndexOf(".");
        imgTitle += imgName.substring(first,last);

        imgTitle = IParser.replaceWhitespaces(imgTitle + ".jpg");
        imgTitle = deleteSquareBrackets(imgTitle);

        return imgTitle;

    }

    private String getImageDir(Path imgName) {
        return imgName.toString().substring(0, imgName.toString().indexOf(".html")) + "_files/";
    }


    private String deleteSquareBrackets(String s) {
        return s.replace("[", "").replace("]", "");
    }



}
