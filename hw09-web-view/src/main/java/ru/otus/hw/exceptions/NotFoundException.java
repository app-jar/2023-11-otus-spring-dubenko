package ru.otus.hw.exceptions;

import org.springframework.http.HttpStatus;


public class NotFoundException extends ClientException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super(STATUS);
    }

    public NotFoundException(final String message) {
        super(message, STATUS);
    }
}
