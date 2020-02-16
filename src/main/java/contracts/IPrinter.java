package main.java.contracts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface IPrinter {


	void printFile(Path destPath, List<IAnkiCard> input) throws IOException;

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
