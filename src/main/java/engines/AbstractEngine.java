package main.java.engines;

import java.nio.file.Path;
import java.nio.file.Paths;

import static main.java.utils.Property.INPUT_DIR;
import static main.java.utils.Property.OUTPUT_DIR;

public abstract class AbstractEngine {

    protected String inputDir;
    protected String outputDir;

    protected String parserInputDir;
    protected String parserOutputDir;

    AbstractEngine() {
        this.inputDir = INPUT_DIR;
        this.outputDir = OUTPUT_DIR;
    }

    AbstractEngine(String inputDir, String outputDir) {
        this();
        this.parserInputDir  = inputDir;
        this.parserOutputDir = outputDir;
    }

    public abstract void buildEngine();

    public Path getFullInputPath() 					{ return Paths.get(getFullInputDir()); }
    public Path getFullOutputPath()					{ return Paths.get(getFullOutputDir()); }
    public String getFullInputDir()					{ return inputDir + parserInputDir; }
    public String getFullOutputDir() 				{ return outputDir + parserOutputDir; }

    public String getInputDir() 					{ return inputDir;}
    public String getOutputDir() 					{ return outputDir; }
    public String getParserInputDir()				{ return parserInputDir; }
    public String getParserOutputDir()				{ return parserOutputDir; }

    public void setInputDir(String inputContent) 	{ this.inputDir = inputContent; }
    public void setOutputDir(String outputDir) 		{ this.outputDir = outputDir; }
    public void setParserInputDir(String content)	{ this.parserInputDir = content; }
    public void setParserOutputDir(String content) 	{ this.parserOutputDir = content; }
}
