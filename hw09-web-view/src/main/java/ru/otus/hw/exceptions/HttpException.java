package ru.otus.hw.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;
    public HttpException(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
