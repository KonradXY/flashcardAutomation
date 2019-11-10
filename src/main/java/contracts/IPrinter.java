package main.java.contracts;

import java.io.IOException;
import java.util.List;

import main.java.baseModel.AbstractAnkiCard;

public interface IPrinter {

	public void printFile(String destPath, List<AbstractAnkiCard> input) throws IOException;
	
	
}
