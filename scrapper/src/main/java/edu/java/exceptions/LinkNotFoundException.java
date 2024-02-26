package edu.java.exceptions;

public class LinkNotFoundException extends RuntimeException {
    public final static String DESCRIPTION = "Ссылка не найдена";

    public LinkNotFoundException(String message) {
        super(message);
    }
}
