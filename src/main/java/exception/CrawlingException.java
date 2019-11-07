package main.java.exception;

public class CrawlingException extends Exception {

    public CrawlingException(Exception ex) {
        super(ex);
    }

}
