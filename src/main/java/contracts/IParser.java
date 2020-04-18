package main.java.contracts;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import main.java.model.AnkiDeck;

public interface IParser {
	
	String REGULAR_PIPE = "|";
	String PIPE_SEPARATOR = "\\|";
	String TAB = "\t";
	String NEW_LINE = "\n";
	
	int DOMANDE_INDEX = 0;
	int RISPOSTE_INDEX = 1;

	Pattern NUM_EX_PATTERN = Pattern.compile("[0-9]+\\.[0-9]");
	Pattern SINGLE_EX_PATTERN = Pattern.compile("[0-9]+\\.");
	String QA_SEPARATOR = "RISPOSTE";

	AnkiDeck parse(Path path, String input);
	Map<Path, AnkiDeck> sort(Map<Path, AnkiDeck> mapContent);

	
	static String replaceNewLines(String input) { return input.replace(NEW_LINE, "");}
	static String replaceWhitespaces(String input) { return input.replace(" ", "");}
}
