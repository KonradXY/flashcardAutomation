package main.java.engines.textengines;
import main.java.engines.TextEngine;
import main.java.model.parsers.EvernoteParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;

/**
 * Questa classe viene usata per il parse del file esportato da evernote nella struttura a tabella (ogni tabella rappresenta una carta)
 */
public class EvernoteEngine extends TextEngine {

	// TODO - c'e' un bug per il quale x l'ultima carta non mi stampa il titolo. Da verificare
	// TODO - non m'ha copiato alcune immagini della big notation x gli algoritmi di ordinamento (fixare sta roba sulle carte anki)

	public EvernoteEngine(String input, String output) { super(input, output); }

	@Override
	public void buildEngine() {
		reader = new TextFileReader();
		parser = new EvernoteParser();
		printer = new TextFilePrinter();
	}
}
