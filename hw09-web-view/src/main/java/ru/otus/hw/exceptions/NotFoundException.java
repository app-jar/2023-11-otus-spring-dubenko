package ru.otus.hw.exceptions;


public class NotFoundException extends RuntimeException {


    public NotFoundException(final String message) {
        super(message);
    }
}
