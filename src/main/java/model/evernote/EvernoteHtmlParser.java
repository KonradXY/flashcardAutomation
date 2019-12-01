package main.java.model.evernote;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IParser;
import main.java.model.AbstractAnkiCard;
import main.java.modelDecorator.CardDecorator;
import main.java.utils.Property;

public class EvernoteHtmlParser implements IParser {

    private final static int MAX_SIZE_CARD = 131072;

    private static final Logger log = Logger.getLogger(EvernoteHtmlParser.class);

    private Path imgInputContent;
    
    public EvernoteHtmlParser() { }
    public EvernoteHtmlParser(Path imgInputContent) {
    	this.imgInputContent = imgInputContent;
    }
    
    public void setImgInputContent(Path imgContent) {
    	this.imgInputContent = imgContent;
    }
    public Path getImgInputContent() {
    	if (imgInputContent == null) return Paths.get(Property.OUTPUT_DIR+Property.EVERNOTE_DIR);
    	return imgInputContent;
    }

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
            if (cardExceedMaxSize(card)) continue;
            cardList.add(card);
        }
        return cardList;
    }

    // FIXME - esiste una size massima per le flashcard. Vedere una soluzione
    private boolean cardExceedMaxSize(AbstractAnkiCard card) {
    	boolean check = card.getValue().text().length() > MAX_SIZE_CARD;
    	if (check) log.info("Card exceded max size ! ");
    	return check;
    }

    private AbstractAnkiCard parseCardFromTBody(Element tbody) {
        Elements content = tbody.getElementsByTag("tr");
        Elements frontElements = getContentFromTrTag(content.get(0));
        Elements backElements = getContentFromTrTag(content.get(1));
        return new AbstractAnkiCard(frontElements, backElements);
    }

    private Elements getContentFromTrTag(Element elem) {
    	Elements elements = elem.getElementsByTag("div");
    	for (Element e : elements) 
    		CardDecorator.applyStandardFormat(e);
    	return elements;
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
            dest = getImgInputContent().resolve(titleImage);
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
