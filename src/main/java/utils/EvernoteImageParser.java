package main.java.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import main.java.model.parsers.IParser;

import static main.java.utils.Property.ANKI_MEDIA_COLLECTION_DIR;

public class EvernoteImageParser {

    private static final Path MEDIA_FOLDER = Paths.get("mediaFolder");
    private static final Logger log = Logger.getLogger(EvernoteImageParser.class);

    public void parseImages(Document doc, Path outputContent, Path imageFile) throws IOException {
        Path currDir = getImageDir(imageFile).getParent();
        Path mediaFolder = getMediaFolder(outputContent);

		copyFiles(doc, imageFile, currDir, mediaFolder);

		copyImagesToMediaCollection(mediaFolder);
    }

	private void copyFiles(Document doc, Path imageFile, Path currDir, Path mediaFolder) {
		String imgTitle;
		Path source;
		for (Element img : doc.getElementsByTag("img")) {
			imgTitle = img.attr("src");
			if (!imageIsCopiable(imgTitle))
				continue;
			source = currDir.resolve(imgTitle);
			String titleImage = getTitleForImage(imageFile, imgTitle);
			copyFile(source, mediaFolder.resolve(titleImage));
			img.attr("src", titleImage);
		}
	}

	private void copyImagesToMediaCollection(Path mediaFolder) throws IOException {
		if (Objects.isNull(mediaFolder) || mediaFolder.toString().equals("")) {
			throw new IllegalStateException("Attenzione: la cartella mediaCollection di anki non e' presente ! I file multimediali non verranno copiati");
		}
		Files.walk(mediaFolder)
				.filter(this::isNotDirectory)
				.forEach(this::copyImage);

	}

    private boolean imageIsCopiable(String imgPath) {
        return !imgPath.contains("http");
    }

	private Path getMediaFolder(Path outputContent) throws IOException {
		Path mediaFolder = outputContent.resolve(MEDIA_FOLDER);
		if (!Files.exists(mediaFolder)) {
			Files.createDirectories(mediaFolder);
		}
		return mediaFolder;
    }

    public void copyFile(Path src, Path dest) {
        if (!Files.exists(src)) {
            throw new IllegalStateException("File src non trovato: " + src);
        }
        if (Files.isDirectory(dest)) {
            throw new IllegalStateException("File non puo essere una directory " + dest);
        }

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Error while copying file: " + ex);
        }

    }

    private String getTitleForImage(Path filePath, String imgName) {

        int first = filePath.toString().lastIndexOf(File.separatorChar) + 1;
        int last = filePath.toString().lastIndexOf('.');
        String imgTitle = filePath.toString().substring(first, last);

        first = imgName.lastIndexOf('/') + 1;
        imgTitle += imgName.substring(first);

        imgTitle = IParser.replaceWhitespaces(imgTitle);
        imgTitle = deleteSquareBrackets(imgTitle);

        return imgTitle;
    }

    public Path getImageDir(Path imgName) {
        return Paths.get(imgName.toString().substring(0, imgName.toString().indexOf(".html")) + "_files/");
    }

    private String deleteSquareBrackets(String s) {
        return s.replace("[", "").replace("]", "");
    }



    private void copyImage(Path imgPath) {
        try {
            Files.copy(imgPath, Paths.get(ANKI_MEDIA_COLLECTION_DIR).resolve(imgPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Errore nella copia dell'immagine: " + imgPath.getFileName(), e);
        }
    }

    private boolean isNotDirectory(Path path) {
        return !Files.isDirectory(path);
    }


}
