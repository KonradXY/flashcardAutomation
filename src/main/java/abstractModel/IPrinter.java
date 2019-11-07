package main.java.abstractModel;

import java.io.IOException;
import java.util.List;

public interface IPrinter {

	public void printFile(String destPath, List<AbstractAnkiCard> input) throws IOException;
	
	
}
