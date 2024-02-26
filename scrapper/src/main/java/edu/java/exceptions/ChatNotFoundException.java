package edu.java.exceptions;

public class ChatNotFoundException extends RuntimeException {
    public final static String DESCRIPTION = "Чат не существует";

    public ChatNotFoundException(String message) {
        super(message);
    }
}
