package main.java.baseModel;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import main.java.abstractModel.AbstractAnkiCard;
import main.java.contracts.IPrinter;

public class SimplePrinter implements IPrinter {

	@Override  
	public void printFile(String destPath, List<AbstractAnkiCard> input) {
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath), "UTF-8"))) {
			for (AbstractAnkiCard ankiCard : input) {
				bos.write(ankiCard.toString());
			}
		} catch (IOException ioex) {
			System.out.println("Errore nella scrittura del file: " + ioex.getMessage());
			throw new RuntimeException(ioex);
		}
	}
	
}
