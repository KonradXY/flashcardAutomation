package main.java.contracts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import main.java.model.AnkiCard;

public interface IPrinter {

	public void printFile(String destPath, List<AnkiCard> input) throws IOException;

	default void checkOutputFolder(Path filePath) {
		try {
			Path folderPath = filePath.getParent();
			if (Files.notExists(folderPath) || !Files.isDirectory(folderPath))
				Files.createDirectories(folderPath);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
