package main.java.model.readers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

public interface IReader {
	
	<T> Map<Path, T> readFile(Path... pathFiles) throws IOException;

	Predicate<Path> isParseable = (IReader::checkParsability);
	
	static boolean filterParsable(Path p) { return checkParsability(p); }
	
	static boolean checkParsability(Path p) {
		String path = p.toString().trim();
		return (path.endsWith(".html") || path.endsWith(".txt"));
	}
	

	
}
