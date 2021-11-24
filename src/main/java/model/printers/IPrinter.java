package main.java.model.printers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import main.java.model.deck.AnkiDeck;

public interface IPrinter {


	void printFile(AnkiDeck deck) throws IOException;

	default void checkOutputFolder(Path filePath) {
		try {
			Path folderPath = filePath.getParent();
			if (!filePath.toFile().exists() || !filePath.toFile().isDirectory())
				Files.createDirectories(folderPath);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}



}
