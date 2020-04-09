package main.java.engines;

import java.nio.file.Path;
import java.nio.file.Paths;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

public abstract class AbstractEngine {

    protected String inputDir;
    protected String outputDir;

    AbstractEngine() {
        this.inputDir = INPUT_DIR;
        this.outputDir = OUTPUT_DIR;
    }

    AbstractEngine(String inputDir, String outputDir) {
        this();
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    public abstract void buildEngine();
    public abstract void createFlashcards();

    public Path getInputAsPath() 					{ return Paths.get(getInputDir()); }
    public Path getOutputAsPath()					{ return Paths.get(getOutputDir()); }

    public String getInputDir() 					{ return inputDir;}
    public String getOutputDir() 					{ return outputDir; }

    public void setInputDir(String inputContent) 	{ this.inputDir = inputContent; }
    public void setOutputDir(String outputDir) 		{ this.outputDir = outputDir; }
}
