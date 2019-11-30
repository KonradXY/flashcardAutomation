package main.java.factory;

import main.java.model.TextFileReader;
import main.java.model.kindle.KindleClippingPrinter;
import main.java.model.kindle.KindleClippingsParser;
import main.java.strategy.FormatStrategy;

public class KindleEngine extends AbstractAnkiEngine {
	
	public KindleEngine() { super(); }
	public KindleEngine(String input, String output) { super(input, output); }
	
	@Override
	public void buildEngine() {
		reader = new TextFileReader(FormatStrategy.REPLACE_NEW_LINES);
		parser = new KindleClippingsParser();
		printer = new KindleClippingPrinter();
	}
}
