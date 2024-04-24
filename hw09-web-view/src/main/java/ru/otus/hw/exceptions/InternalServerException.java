package ru.otus.hw.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerException extends HttpException {

    public InternalServerException() {
        this(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(final HttpStatus httpStatus) {
        super(httpStatus);
    }

    public InternalServerException(final String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
