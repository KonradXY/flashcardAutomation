package main.java.contracts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

public interface IReader {
	
	public Map<Path, String> readFile(Path... pathFiles) throws IOException;

	public static Predicate<Path> isParseable = (IReader::checkParsability);
	
	public static boolean isParseable(Path p) { return checkParsability(p); }
	
	public static boolean checkParsability(Path p) {
		String path = p.toString().trim();
		if (path.endsWith(".html") || path.endsWith(".txt"))
			return true;
		return false;
	}
	

	
}
