package ru.otus.hw.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleBadRequest(EntityNotFoundException ex) {
        log.error("Entity not found", ex);
        return new ResponseEntity<>("Not found:\n" + ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String handleBadRequest(RuntimeException ex) {
        log.error("Invalid data exception", ex);
        return "Request exception:\n" + ex.getMessage();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return new ResponseEntity<>("Internal server exception:\n" + ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
