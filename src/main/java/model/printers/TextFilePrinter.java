package main.java.model.printers;

import org.apache.log4j.Logger;

import main.java.model.deck.AnkiDeck;

public class TextFilePrinter implements IPrinter {

	private static final Logger log = Logger.getLogger(TextFilePrinter.class);

	private final PrintStrategy printStrategy;

	public  TextFilePrinter() {
		this.printStrategy = PrintStrategy.NO_STRATEGY;
	}

	public TextFilePrinter(PrintStrategy printStrategy) {
		this.printStrategy = printStrategy;
	}
	
	@Override
	public void printFile(AnkiDeck deck) {
		try {
			checkOutputFolder(deck.getPathDest());
			printStrategy.printCards(deck);
		} catch (Exception ex) {
			log.error("Errore per la funzione printFile", ex);
		}

	}
	
}
