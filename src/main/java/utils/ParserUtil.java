package main.java.utils;

import main.java.contracts.IParser;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ParserUtil {

    private static final Logger log = Logger.getLogger(ParserUtil.class);
    
    Path imgInputContent;

    public void setImgInputContent(Path imgInputContent) {
        this.imgInputContent = imgInputContent;
    }

    public Path getImgInputContent() {
        return this.imgInputContent;
    }

    public void setImagesForFlashcard(Document doc, Path currDir, Path fileName) {
        String imgTitle;
        Path dest;
        Path source;

        for (Element img : doc.getElementsByTag("img")) {

            if (img.attr("src").contains("http"))   // FIXME <<-- quickfix qua dentro. Fare in modo di pulire l'html da queste immagini fasulle (e difatti nn funge nemmeno)
                continue;

            imgTitle = img.attr("src");
            log.info("IMG TITLE: " + imgTitle);

            source = currDir.resolve(imgTitle);
            log.info("SOURCE: " + source);

            String titleImage = getTitleForImage(fileName, imgTitle);
            log.info("TITLE IMAGE: " + titleImage);

            dest = getImgInputContent().resolve(titleImage);
            log.info("DEST: " + dest);

            copyFile(source, dest);
            img.attr("src", getTitleForImage(fileName, imgTitle));
        }
    }

    public void copyFile(Path src, Path dest) {
        if (!Files.exists(src))
            throw new RuntimeException("FILE SRC NON TROVATO: " + src);

        if (Files.isDirectory(dest)) {
            throw new RuntimeException("FILE DEST NON PUO' ESSERE DIRECTORY ! " + dest);
        }

        // FIXME - all'interno della cartella di output ci sono ancora le estensioni dei file ... fixare
        // dest = Paths.get(dest.toString().replace(".txt",""));
        
        // FIXME - se non esiste la cartella di output va in eccezione. Fixare !
        
        // FIXME - fare in modo che l'estensione delle immagini sia la stessa !

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

    public String getImageDir(Path imgName) {
        return imgName.toString().substring(0, imgName.toString().indexOf(".html")) + "_files/";
    }

    private String deleteSquareBrackets(String s) {
        return s.replace("[", "").replace("]", "");
    }

}
