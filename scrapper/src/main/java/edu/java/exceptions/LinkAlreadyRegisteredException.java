package edu.java.exceptions;

public class LinkAlreadyRegisteredException extends RuntimeException {
    public final static String DESCRIPTION = "Ссылка уже зарегистрирована";

    public LinkAlreadyRegisteredException(String message) {
        super(message);
    }
}
