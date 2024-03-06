package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class ChatAlreadyRegisteredException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Чат уже зарегистрирован";
    public final static HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public ChatAlreadyRegisteredException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
