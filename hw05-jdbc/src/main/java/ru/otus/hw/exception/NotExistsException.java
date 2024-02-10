package ru.otus.hw.exception;

public class NotExistsException extends RuntimeException {
    public NotExistsException(String msg) {
        super(msg);
    }
}
