package main.java.factory;

import static main.java.utils.Property.KINDLE_DIR;

import main.java.model.TextFileReader;
import main.java.model.kindle.KindleClippingPrinter;
import main.java.model.kindle.KindleClippingsParser;
import main.java.strategy.FormatStrategy;

public class KindleEngine extends AbstractAnkiEngine {
	@Override
	public void buildEngine() {
		inputContent  += KINDLE_DIR;
		outputContent += KINDLE_DIR;
		
		reader = new TextFileReader(FormatStrategy.REPLACE_NEW_LINES);
		parser = new KindleClippingsParser();
		printer = new KindleClippingPrinter();
	}
}
