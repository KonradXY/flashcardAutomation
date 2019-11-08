package main.java.contracts;

import main.java.webcrawlers.AbstractWebCrawler;

public interface WebCrawlerMediator {

    public void mediate(AbstractWebCrawler wc1, AbstractWebCrawler wc2);
}
