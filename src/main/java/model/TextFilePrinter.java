package main.java.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;

public class TextFilePrinter implements IPrinter {
	
	@Override
	public void printFile(Path destPath, List<IAnkiCard> input) {
		this.printFile(destPath.toString(), input);
	}

	@Override  
	public void printFile(String destFile, List<IAnkiCard> input) {

		Path destPath = Paths.get(destFile);
		checkOutputFolder(destPath);

		try {
			Files.write(destPath, (Iterable<String>) input.stream().map(it -> it.toString())::iterator);
		} catch (IOException ex) {
			System.out.println("Errore nella scrittura del file: " + ex.getMessage());
			throw new RuntimeException(ex);
		}

	}
	
}
