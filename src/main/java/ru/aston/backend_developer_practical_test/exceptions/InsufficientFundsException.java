package ru.aston.backend_developer_practical_test.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException (String message) {
        super(message);
    }
}
