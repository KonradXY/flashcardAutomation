package main.exceptions;

public class WebCrawlerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

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
