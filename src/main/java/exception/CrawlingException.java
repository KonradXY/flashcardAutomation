package main.java.exception;

public class CrawlingException extends Exception {

	private static final long serialVersionUID = 1L;

	public CrawlingException(Exception ex) {
        super(ex);
    }

}
