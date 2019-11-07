package main.java.evernoteModel;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.abstractModel.IParser;
import main.java.baseModel.SimpleAnkiCard;
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

public class EvernoteHtmlParser implements IParser {

    private final static int MAX_SIZE_CARD = 131072;

    private static final Logger log = Logger.getLogger(EvernoteHtmlParser.class);

    private static final String MARGIN = "margin", AUTO = "auto";
    private static final String ALIGN = "align", LEFT = "left";
    private static final String TEXT_ALIGN = "text-align";
    private static final String FONT_STYLE = "font style";
    private static final String FONT_SIZE = "font-size: 10pt";

    @Override
    public List<AbstractAnkiCard> parseToAnkiFlashcard(Map<Path, String> input) {    
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
        String title = parseTitleFromFilename(fileName);

        for (Element tbody : doc.getElementsByTag("tbody")) {
            AbstractAnkiCard card = parseCardFromTBody(tbody);
            formatFrontAndBack(card, title);

            // FIXME
            if (card.getValue().length() > MAX_SIZE_CARD) {
                log.info("Card exceed max size ! ");
                continue;
            }

            cardList.add(card);
        }
        return cardList;
    }

    private void formatFrontAndBack(AbstractAnkiCard card, String title) {
        formatFrontPart(card, title);
        formatBackPart(card);
    }

    private void formatFrontPart(AbstractAnkiCard card, String title) {

        if (!card.getKey().contains("D:"))
            throw new RuntimeException("Errore. Nella carta non e' presente il simbolo - D: - ");

        int firstIndex = title.lastIndexOf("\\") + 1;
        String titleCard = title.substring(firstIndex);
        card.setKey(card.getKey().replace("D:", titleCard));

    }

    private void formatBackPart(AbstractAnkiCard card) {
        card.setValue(card.getValue().replace("R:", ""));
    }

    private String parseTitleFromFilename(Path filePath) {
    	String fileName = filePath.toString();
        int firstIndex = fileName.lastIndexOf("/")+1;
        int lastIndex = fileName.lastIndexOf(".")+1;
        return fileName.substring(firstIndex, lastIndex);
    }

    private AbstractAnkiCard parseCardFromTBody(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        String cardFront = formatRowContent(content.get(0));
        String cardBack = formatRowContent(content.get(1));
        return new SimpleAnkiCard(cardFront, cardBack);
    }

    private String formatRowContent(Element elem) {
        for (Element div : elem.getElementsByTag("div"))
            formatNodeElement(div);

        return replaceNewLines(elem.toString());
    }

    private Element formatNodeElement(Element elem) {
        removeUselessAttrs(elem);
        return elem.attr(ALIGN, LEFT)
                .attr(TEXT_ALIGN, LEFT)
                .attr(FONT_STYLE, FONT_SIZE)
                .attr(MARGIN, AUTO);
    }

    private void removeUselessAttrs(Element elem) {
        elem.removeAttr("style");
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
        }catch (IOException ex) {
            throw new RuntimeException("Error while copying file: " + ex);
        }

    }

    private String createTitleForImage(Path filePath, String imgName) {
        return getPathForImage(filePath)+getTitleForImage(filePath, imgName);
    }

    private String getPathForImage(Path filePath) {
        String fileName = filePath.toString();
        int firstIndex = fileName.lastIndexOf("/")+1;
        int lastIndex = fileName.lastIndexOf(".");
        String firstString = replaceWhitespaces(fileName.substring(firstIndex, lastIndex)).replace("input","output"); // <<--- FIXME

        log.info("firstString " + firstString);
        return firstString;
    }

    private String getTitleForImage(Path filePath, String imgName) {

        int first = filePath.toString().lastIndexOf(File.separatorChar)+1;
        int last = filePath.toString().lastIndexOf(".");
        String imgTitle = filePath.toString().substring(first, last);

        first = imgName.lastIndexOf("/") + 1;
        last = imgName.lastIndexOf(".");
        imgTitle += imgName.substring(first,last);

        imgTitle = replaceWhitespaces(imgTitle + ".jpg");
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
