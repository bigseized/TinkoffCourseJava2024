package edu.java.exceptions;

import org.springframework.http.HttpStatus;

public class UnsupportedLinkFormatException extends ApiErrorResponseException {
    public final static String DESCRIPTION = "Формат ссылки не поддерживается";
    public final static HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public UnsupportedLinkFormatException(String message) {
        super(message, DESCRIPTION, HTTP_STATUS);
    }
}
