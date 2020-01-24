package main.java.engines;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

public class AbstractEngine {

    protected String inputDir;
    protected String outputDir;

    protected String parserInputDir;
    protected String parserOutputDir;

    AbstractEngine() {
        this.inputDir = INPUT_DIR;
        this.outputDir = OUTPUT_DIR;
    }

    AbstractEngine(String inputDir, String outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }
}
