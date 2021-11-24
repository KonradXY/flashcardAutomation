package main.java.model.parsers;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import main.java.model.deck.AnkiDeck;

public interface IParser {

	// TODO - create enum constant
	String REGULAR_PIPE = "|";
	String PIPE_SEPARATOR = "\\|";
	String TAB = "\t";
	String NEW_LINE = "\n";
	
	int DOMANDE_INDEX = 0;
	int RISPOSTE_INDEX = 1;

	Pattern NUM_EX_PATTERN = Pattern.compile("[0-9]+\\.[0-9]");
	Pattern SINGLE_EX_PATTERN = Pattern.compile("[0-9]+\\.");
	Pattern SINGLE_EX_PATTERN_WITH_UNDERSCORE = Pattern.compile("[0-9]+\\_");
	String QA_SEPARATOR = "RISPOSTE";

	List<AnkiDeck> parse(Path path, String input, String destFolder);
	
	static String replaceNewLines(String input) { return input.replace(NEW_LINE, "");}
	static String replaceWhitespaces(String input) { return input.replace(" ", "");}
	
	default String getParsedFileName(Path inputFile) {
		String textName = new File(inputFile.toString()).getName();
		String extension = textName.substring(textName.lastIndexOf('.'));
		return textName.replace(extension, "_parsed");
	}
}
