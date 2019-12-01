package main.java.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	public void printFile(String destPath, List<IAnkiCard> input) {
		
		checkOutputFolder(Paths.get(destPath));
		
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath), "UTF-8"))) {
			for (IAnkiCard ankiCard : input) {
				bos.write(ankiCard.toString());
			}
		} catch (IOException ioex) {
			System.out.println("Errore nella scrittura del file: " + ioex.getMessage());
			throw new RuntimeException(ioex);
		}
	}
	
}
