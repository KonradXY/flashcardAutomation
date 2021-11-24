package main.java.model.printers;

import org.apache.log4j.Logger;

import main.java.model.deck.AnkiDeck;

public class TextFilePrinter implements IPrinter {

	private static final Logger log = Logger.getLogger(TextFilePrinter.class);

	private PrinterStrategy printerStrategy;

	public TextFilePrinter(PrinterStrategy printerStrategy) {
		this.printerStrategy = printerStrategy;
	}
	
	@Override
	public void printFile(AnkiDeck deck) {
		try {
			checkOutputFolder(deck.getPathDest());
			printerStrategy.printCards(deck);
		} catch (Exception ex) {
			log.error("Errore nella scrittura su disco", ex);
		}

	}
	
}
