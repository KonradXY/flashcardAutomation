package main.java.contracts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

public interface IReader {
	
	public <T> Map<Path, T> readFile(Path... pathFiles) throws IOException;

	public static Predicate<Path> isParseable = (IReader::checkParsability);
	
	public static boolean filterParsable(Path p) { return checkParsability(p); }
	
	public static boolean checkParsability(Path p) {
		String path = p.toString().trim();
		return (path.endsWith(".html") || path.endsWith(".txt"));
	}
	

	
}
