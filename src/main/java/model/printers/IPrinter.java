package main.java.model.printers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import main.java.model.deck.AnkiDeck;

public interface IPrinter {


	void printFile(AnkiDeck deck) throws IOException;

	default void checkOutputFolder(Path filePath) throws IOException {
			Path folderPath = filePath.getParent();
			if (!filePath.toFile().exists() || !filePath.toFile().isDirectory())
				Files.createDirectories(folderPath);
	}



}
