package main.java.factory;

import static main.java.utils.Property.*;

import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;

public abstract class AbstractAnkiEngine {
	
	protected IReader reader;
	protected IParser parser;
	protected IPrinter printer;
	
	protected String inputDir;
	protected String outputDir;
	
	protected String inputContent;
	protected String outputContent;

	protected AbstractAnkiEngine() {
		this.inputDir = INPUT_DIR;
		this.outputDir = OUTPUT_DIR;
	}
	
	protected AbstractAnkiEngine(String input, String output) {
		this();
		this.inputContent  = input;
		this.outputContent = output;
	}
	
	
	public abstract void buildEngine();
	
	public IReader getReader() 						{ return reader; }
	public IParser getParser() 						{ return parser; }
	public IPrinter getPrinter() 					{ return printer; }

	public void setReader(IReader reader) 			{ this.reader = reader; }
	public void setParser(IParser parser) 			{ this.parser = parser; }
	public void setPrinter(IPrinter printer) 		{ this.printer = printer; }
	
	public String getInputDestination()				{ return inputDir + inputContent; }
	public String getOutputDestination() 			{ return outputDir + outputContent; }
	public String getInputDir() 					{ return inputDir;}
	public String getOutputDir() 					{ return outputDir; }
	public String getInputContent()					{ return inputContent; }
	public String getOutputContent()				{ return outputContent; }
	
	public void setInputDir(String inputContent) 	{ this.inputDir = inputContent; }
	public void setOutputDir(String outputDir) 		{ this.outputDir = outputDir; }
	public void setInputContent(String content)		{ this.inputContent = content; }
	public void setOutputContent(String content) 	{ this.outputContent = content; }
}
