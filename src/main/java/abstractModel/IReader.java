package main.java.abstractModel;

import java.nio.file.Path;
import java.util.function.Predicate;

public interface IReader {
	
	public String formatLine(String input);

	
	
	//***** concrete methods
	public static boolean isParseable(Path p) {
		return checkParsability(p);
	}

	public static Predicate<Path> isParseable = (it -> checkParsability(it));
	
	public static boolean checkParsability(Path p) {
		String path = p.toString().trim();
		if (path.endsWith(".html") || path.endsWith(".txt"))
			return true;
		return false;
	}
	

	
}
