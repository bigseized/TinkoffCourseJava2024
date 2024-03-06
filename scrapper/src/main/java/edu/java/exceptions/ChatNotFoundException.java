package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class ChatNotFoundException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Чат c заданным ID не существует";
    public final static HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ChatNotFoundException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
