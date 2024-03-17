package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class LinkFormatUnsupportedException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Формат ссылки не поддерживается";
    public final static HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public LinkFormatUnsupportedException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
