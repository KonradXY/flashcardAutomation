package main.java.factory;

import static main.java.utils.Property.KINDLE_DIR;

import main.java.baseModel.SimpleReader;
import main.java.kindleModel.KindleClippingPrinter;
import main.java.kindleModel.KindleClippingsParser;
import main.java.strategy.ReadingStrategy;

public class KindleEngine extends AbstractAnkiEngine {
	@Override
	public void buildEngine() {
		inputContent  += KINDLE_DIR;
		outputContent += KINDLE_DIR;
		
		reader = new SimpleReader(ReadingStrategy.KINDLE);
		parser = new KindleClippingsParser();
		printer = new KindleClippingPrinter();
	}
}
