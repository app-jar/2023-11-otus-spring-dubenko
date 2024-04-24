package ru.otus.hw.exceptions;

import org.springframework.http.HttpStatus;

public class ClientException extends HttpException {

    public ClientException(final HttpStatus status) {
        super(status);
    }

    public ClientException(final String message, final HttpStatus status) {
        super(message, status);
    }
}
