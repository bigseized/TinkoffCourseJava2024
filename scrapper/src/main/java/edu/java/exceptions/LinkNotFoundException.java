package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class LinkNotFoundException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Ссылка не найдена";
    public final static HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public LinkNotFoundException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
