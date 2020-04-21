package main.java.model.parsers;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.contracts.IParser;
import main.java.model.AnkiDeck;
import main.java.strategy.TextParsingStrategy;
import main.java.utils.Property;

public class LanguageLearningParser implements IParser {
	
	private static final Logger log = Logger.getLogger(LanguageLearningParser.class);

	@Override
	public List<AnkiDeck> parse(Path path, String file, String destFolder) {

		String fileName = path.toString().trim();
		AnkiDeck deck = new AnkiDeck.Builder()
				.withDestFolder(destFolder)
				.withTitle(getParsedFileName(path))
				.build();

		if 		(fileName.endsWith("grammatica.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_GRAMMATICA.parseFile(deck, file);

		else if (fileName.endsWith("vocabolario.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_VOCABOLARIO.parseFile(deck, file);

		else if (fileName.endsWith("traduzioni.txt"))
			TextParsingStrategy.PRACTICE_MAKES_PERFECT_TRADUZIONI.parseFile(deck, file);

		else if (fileName.endsWith("parole.txt"))
			TextParsingStrategy.SIMPLE_PARSER.parseFile(deck, file);

		else {
			log.info("Trovato file non contemplato per il parser LanguageLearning: " + path
					+ ". Il file verrà scartato. ");
		}

		return Arrays.asList(deck);
	}
	
}
