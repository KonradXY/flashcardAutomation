package main.java.contracts;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface IParser {
	
	public static String REGULAR_PIPE = "|";
	public static String PIPE_SEPARATOR = "\\|";
	public static String TAB = "\t";
	public static String NEW_LINE = "\n";
	
	public static final int DOMANDE_INDEX = 0;
	public static final int RISPOSTE_INDEX = 1;

	public static Pattern NUM_EX_PATTERN = Pattern.compile("[0-9]+\\.[0-9]");
	public static Pattern SINGLE_EX_PATTERN = Pattern.compile("[0-9]+\\.");
	public static String QA_SEPARATOR = "RISPOSTE";	

	// Map<filename,filecontent>
	public List<IAnkiCard> parse(Map<Path, String> input);
	
	public static String replaceNewLines(String input) { return input.replace(NEW_LINE, "");}
	public static String replaceWhitespaces(String input) { return input.replace(" ", "");}
}
