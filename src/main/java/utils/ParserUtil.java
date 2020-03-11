package main.java.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;

public class ParserUtil {

	private static final Path MEDIA_FOLDER = Paths.get("mediaFolder");
	private static final Logger log = Logger.getLogger(ParserUtil.class);
	private static final int MAX_SIZE_CARD = 131072;

	public boolean cardExceedMaxSize(IAnkiCard card) {
		boolean check = card.getBack().text().length() > MAX_SIZE_CARD || card.getFront().text().length() > MAX_SIZE_CARD;
		if (check) {
			log.info("Card exceded max size ! Card title: " + card.getFront().text());
		}
		return check;
	}
	
	// TODO - dovrei spezzare in piu' funzioni semplici sta roba

	public void createImagesForFlashcard(Document doc, Path outputContent, Path imageFile) {
		String imgTitle;
		Path source;

		Path currDir = getImageDir(imageFile).getParent();
		Path dest = buildMediaFolder(outputContent);

		for (Element img : doc.getElementsByTag("img")) {
			imgTitle = img.attr("src");
			if (!imageIsCopiable(imgTitle))
				continue;
			source = currDir.resolve(imgTitle);
			String titleImage = getTitleForImage(imageFile, imgTitle);
			copyFile(source, dest.resolve(titleImage));
			img.attr("src", titleImage);
		}
	}

	private boolean imageIsCopiable(String imgPath) {
		return !imgPath.contains("http");
	}

	public Path buildMediaFolder(Path outputContent) {
		try {
			Path mediaFolder = outputContent.resolve(MEDIA_FOLDER);
			if (!Files.exists(mediaFolder)) {
				Files.createDirectories(mediaFolder);
			}
			return mediaFolder;
		} catch (IOException ex) {
			log.error("Cannot create media folder !");
			throw new IllegalStateException(ex);
		}
	}

	public void copyFile(Path src, Path dest) {

		if (!Files.exists(src)) {
			throw new IllegalStateException("FILE SRC NON TROVATO: " + src);
		}

		if (Files.isDirectory(dest)) {
			throw new IllegalStateException("FILE DEST NON PUO' ESSERE DIRECTORY ! " + dest);
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

}
