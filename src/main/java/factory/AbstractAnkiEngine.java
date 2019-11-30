package main.java.factory;

import static main.java.utils.Property.*;

import main.java.contracts.IParser;
import main.java.contracts.IPrinter;
import main.java.contracts.IReader;

public abstract class AbstractAnkiEngine {

	protected IReader reader;
	protected IParser parser;
	protected IPrinter printer;
	
	protected String inputContent = INPUT_DIR;
	protected String outputContent = OUTPUT_DIR;
	
	public abstract void buildEngine();
	
	public IReader getReader() {
		return reader;
	}
	public void setReader(IReader reader) {
		this.reader = reader;
	}
	public IParser getParser() {
		return parser;
	}
	public void setParser(IParser parser) {
		this.parser = parser;
	}
	public IPrinter getPrinter() {
		return printer;
	}
	public void setPrinter(IPrinter printer) {
		this.printer = printer;
	}
	public String getInputContent() {
		return inputContent;
	}
	public void setInputContent(String inputContent) {
		this.inputContent = inputContent;
	}
	public String getOutputContent() {
		return outputContent;
	}
	public void setOutputContent(String outputContent) {
		this.outputContent = outputContent;
	}
}
