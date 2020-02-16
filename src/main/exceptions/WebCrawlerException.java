package main.exceptions;

public class WebCrawlerException extends RuntimeException {

    public WebCrawlerException() {
        super();
    }

    public WebCrawlerException(String msg) {
        super(msg);
    }

    public WebCrawlerException(Throwable err) {
        super(err);
    }
}
