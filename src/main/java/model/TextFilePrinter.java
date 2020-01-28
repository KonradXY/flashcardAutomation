package main.java.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;

public class TextFilePrinter implements IPrinter {
	
	@Override
	public void printFile(Path destPath, List<IAnkiCard> input) {

		checkOutputFolder(destPath);

		try {
			Files.write(destPath, (Iterable<String>) input.stream().map(it -> it.toString())::iterator);
		} catch (IOException ex) {
			System.out.println("Errore nella scrittura del file: " + ex.getMessage());
			throw new RuntimeException(ex);
		}

	}
	
}
