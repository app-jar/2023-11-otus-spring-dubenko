package ru.otus.hw.exception;

public class SaveError extends RuntimeException {
    public SaveError(String msg, Exception cause) {
        super(msg + "\ncause: " + cause.getMessage());
    }
}
