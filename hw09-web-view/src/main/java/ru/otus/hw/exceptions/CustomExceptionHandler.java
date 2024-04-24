package ru.otus.hw.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;




@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ClientException.class})
    public ResponseEntity<String> handleBadRequest(ClientException ex) {
        return new ResponseEntity<>("Request exception:\n" + ex.getMessage(),
                new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return new ResponseEntity<>("Internal server exception:\n" + ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
