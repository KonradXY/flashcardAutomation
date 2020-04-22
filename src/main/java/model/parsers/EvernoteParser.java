package main.java.model.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import main.java.card_decorators.StandardCardDecorator;
import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiCard;
import main.java.model.AnkiDeck;
import main.java.utils.ParserUtil;
import main.java.utils.Property;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static main.java.utils.Property.ANKI_MEDIA_COLLECTION_DIR;

public class EvernoteParser implements IParser {


	private static final Logger log = Logger.getLogger(EvernoteParser.class);

	private ParserUtil parserUtil;

	public EvernoteParser() { }

	public EvernoteParser(ParserUtil parserUtil) {
		this.parserUtil = parserUtil;
	}


	@Override
	public List<AnkiDeck> parse(Path filename, String input, String destFolder) {
		AnkiDeck deck = new AnkiDeck.Builder()
				.withDestFolder(destFolder)
				.withTitle(getParsedFileName(filename))
				.build();
		deck.getCards().addAll(parseEvernoteFlashCards(filename, input, Paths.get(destFolder)));
		return Arrays.asList(deck);
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

		if (Objects.isNull(ANKI_MEDIA_COLLECTION_DIR) || ANKI_MEDIA_COLLECTION_DIR.equals("")) {
			log.info("Attenzione: la cartella mediaCollection di anki non e' presente ! I file multimediali non verranno copiati");
			return;
		}

		Path mediaFolder = parserUtil.buildMediaFolder(outputContent);
		
		Files.walk(mediaFolder).filter(this::isNotDirectory).forEach(imgPath ->{
			try {
				Path imgName = imgPath.getFileName();
				Files.copy(imgPath, Paths.get(ANKI_MEDIA_COLLECTION_DIR).resolve(imgName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		
		log.info("Anki media files copied inside folder: " + ANKI_MEDIA_COLLECTION_DIR);
	}
	
	private boolean isNotDirectory(Path path) {
		return !Files.isDirectory(path);
	}



}
