package main.java.model.evernote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.card_decorators.StandardCardDecorator;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiCard;
import main.java.utils.ParserUtil;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static main.java.utils.Property.ANKI_MEDIA_COLLECTION_DIR;

public class EvernoteHtmlParser implements IParser {


	private static final Logger log = Logger.getLogger(EvernoteHtmlParser.class);

	private ParserUtil parserUtil;
	private Path outputContent;

	public EvernoteHtmlParser() { }

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
		
		try {
			copyImagesToMediaCollection(outputContent);
		} catch (IOException ex) {
			log.error("Errore nella copia delle immagini nel media collector.", ex);
		}

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

	private void copyImagesToMediaCollection(Path outputContent) throws IOException {
		Path mediaFolder = parserUtil.buildMediaFolder(outputContent);
		
		Files.walk(mediaFolder).filter(this::isNotDirectory).forEach(imgPath ->{
			try {
				Path imgName = imgPath.getFileName();
				log.info("Anki media files copied: " + Paths.get(ANKI_MEDIA_COLLECTION_DIR).resolve(imgName));
				Files.copy(imgPath, Paths.get(ANKI_MEDIA_COLLECTION_DIR).resolve(imgName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	private boolean isNotDirectory(Path path) {
		return !Files.isDirectory(path);
	}



}
