package main.java.factory;

import static main.java.utils.Property.KINDLE_DIR;

import main.java.model.kindleModel.KindleClippingPrinter;
import main.java.model.kindleModel.KindleClippingsParser;
import main.java.model.simplemodel.SimpleReader;
import main.java.strategy.FormatStrategy;

public class KindleEngine extends AbstractAnkiEngine {
	@Override
	public void buildEngine() {
		inputContent  += KINDLE_DIR;
		outputContent += KINDLE_DIR;
		
		reader = new SimpleReader(FormatStrategy.REPLACE_NEW_LINES);
		parser = new KindleClippingsParser();
		printer = new KindleClippingPrinter();
	}
}
