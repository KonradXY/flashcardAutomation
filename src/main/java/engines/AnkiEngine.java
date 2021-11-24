package main.java.engines;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AnkiEngine {

    protected String inputDir;
    protected String outputDir;

    AnkiEngine(String inputDir, String outputDir) {
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
