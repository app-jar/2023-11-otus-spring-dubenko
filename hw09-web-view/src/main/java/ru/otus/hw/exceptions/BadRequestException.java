package ru.otus.hw.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ClientException {
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
        super(STATUS);
    }

    public BadRequestException(final String message) {
        super(message, STATUS);
    }
}
