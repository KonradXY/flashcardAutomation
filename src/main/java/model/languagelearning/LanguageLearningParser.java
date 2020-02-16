package main.java.model.languagelearning;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.strategy.TextParsingStrategy;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageLearningParser implements IParser {
	
	private static final Logger log = Logger.getLogger(LanguageLearningParser.class);

	@Override
	public List<IAnkiCard> parse(Path path, String file) {

		String fileName = path.toString().trim();
		List<IAnkiCard> cardList = new ArrayList<>();

		if 		(fileName.endsWith("grammatica.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_GRAMMATICA.parseFile(cardList, file);

		else if (fileName.endsWith("vocabolario.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_VOCABOLARIO.parseFile(cardList, file);

		else if (fileName.endsWith("traduzioni.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_TRADUZIONI.parseFile(cardList, file);

		else if (fileName.endsWith("parole.txt"))
			TextParsingStrategy.SIMPLE_PARSER.parseFile(cardList, file);

		else {
			log.info("Trovato file non contemplato per il parser LanguageLearning: " + path
					+ ". Il file verrà scartato. ");
		}

		return cardList;
	}

	@Override
	public Map<Path, List<IAnkiCard>> sort(Map<Path, List<IAnkiCard>> mapContent) {
		return mapContent;
	}
	

	

	



}
