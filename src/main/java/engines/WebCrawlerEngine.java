
package main.java.engines;

import main.java.contracts.IPrinter;
import main.java.contracts.IReader;
import main.java.contracts.IWebCrawler;

public abstract class WebCrawlerEngine extends AbstractEngine {

    protected IReader reader;
    protected IWebCrawler webCrawler;
    protected IPrinter printer;


    public IReader getReader() { return reader; }
    public IWebCrawler getWebCrawler() { return webCrawler; }
    public IPrinter getPrinter() { return printer; }

    public void setReader(IReader reader) {
        this.reader = reader;
    }

    public void setWebCrawler(IWebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    public void setPrinter(IPrinter printer) {
        this.printer = printer;
    }
}
