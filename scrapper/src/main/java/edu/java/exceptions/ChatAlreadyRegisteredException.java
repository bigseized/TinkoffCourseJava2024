package edu.java.exceptions;

public class ChatAlreadyRegisteredException extends RuntimeException {
    public final static String DESCRIPTION = "Чат уже зарегистрирован";

    public ChatAlreadyRegisteredException(String message) {
        super(message);
    }
}
