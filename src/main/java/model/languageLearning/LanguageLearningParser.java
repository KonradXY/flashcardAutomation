package main.java.model.languageLearning;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.strategy.TextParsingStrategy;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LanguageLearningParser implements IParser {
	
	private final static Logger log = Logger.getLogger(LanguageLearningParser.class);
	
	@Override
	public List<IAnkiCard> parse(Map<Path, String> input) {
		List<IAnkiCard> cardList = new ArrayList<>();

		for (Map.Entry<Path, String> file : input.entrySet()) {
			
			String fileName = file.getKey().toString().trim();
			
			if 		(fileName.endsWith("grammatica.txt"))
				TextParsingStrategy.PRACTICE_MAKES_PERFECT_GRAMMATICA.parseFile(cardList, file.getValue());

			else if (fileName.endsWith("vocabolario.txt"))
				TextParsingStrategy.PRACTICE_MAKES_PERFECT_VOCABOLARIO.parseFile(cardList, file.getValue());

			else if (fileName.endsWith("traduzioni.txt"))
				TextParsingStrategy.PRACTICE_MAKES_PERFECT_TRADUZIONI.parseFile(cardList, file.getValue());
			
			else if (fileName.endsWith("parole.txt"))
				TextParsingStrategy.SIMPLE_PARSER.parseFile(cardList, file.getValue());
			
			else {
				log.info("Trovato file non contemplato per il parser LanguageLearning: " + file.getKey() 
				+ ". Il file verrà scartato. ");
				continue;
			}

		}
		return cardList;
	}
	

	

	



}
