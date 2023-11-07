package ru.aston.backend_developer_practical_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSearchResultException.class)
    public ResponseEntity<Object> handleNoSearchResultException(NoSearchResultException message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {InvalidPinException.class, InsufficientFundsException.class})
    public ResponseEntity<Object> handleInvalidPinOrInsufficientFunds(RuntimeException message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message.getMessage());
    }

}
