package main.java.contracts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import main.java.model.AnkiDeck;

public interface IPrinter {


	void printFile(AnkiDeck deck) throws IOException;

	default void checkOutputFolder(Path filePath) {
		try {
			Path folderPath = filePath.getParent();
			if (!filePath.toFile().exists() || !filePath.toFile().isDirectory())
				Files.createDirectories(folderPath);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}



}
