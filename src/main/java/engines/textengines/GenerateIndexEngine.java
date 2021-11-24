package main.java.engines.textengines;

import main.java.engines.TextEngine;
import main.java.model.parsers.GenerateIndexParser;
import main.java.model.printers.TextFilePrinter;
import main.java.model.readers.TextFileReader;
import main.java.model.printers.PrinterStrategy;
import main.java.model.readers.ReadingFormatStrategy;

public class GenerateIndexEngine extends TextEngine {

    public GenerateIndexEngine(String input, String output) {
        super(input, output);
    }

    @Override
    public void buildEngine() {
        reader = new TextFileReader(ReadingFormatStrategy.ADD_NEW_LINE);
        parser = new GenerateIndexParser();
        printer = new TextFilePrinter(PrinterStrategy.NO_STRATEGY);
    }

}
