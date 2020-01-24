package main.java.engines;

import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.contracts.IWebCrawler;

public class WebCrawlerEngine  {

    protected IReader reader;
    protected IWebCrawler spanishWebCrawlerService;
    protected IPrinter printer;

    protected String inputDir;
    protected String outputDir;

    protected String parserInputDir;
    protected String parserOutputDir;



}
