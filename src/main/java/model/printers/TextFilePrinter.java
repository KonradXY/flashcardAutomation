package main.java.model.printers;

import java.nio.file.Path;
import java.util.List;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IPrinter;
import main.java.model.AnkiDeck;
import main.java.strategy.PrinterStrategy;
import org.apache.log4j.Logger;

public class TextFilePrinter implements IPrinter {

	private static final Logger log = Logger.getLogger(TextFilePrinter.class);

	private PrinterStrategy printerStrategy;

	public TextFilePrinter(PrinterStrategy printerStrategy) {
		this.printerStrategy = printerStrategy;
	}
	
	@Override
	public void printFile(AnkiDeck deck) {

		Path destPath = printerStrategy.createNameOutputFile(deck.getPathDest());

		checkOutputFolder(destPath);
		
		printerStrategy.printCards(deck);

	}
	
}
