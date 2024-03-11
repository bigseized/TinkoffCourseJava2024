package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class LinkAlreadyRegisteredException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Ссылка уже зарегистрирована";
    public final static HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public LinkAlreadyRegisteredException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
