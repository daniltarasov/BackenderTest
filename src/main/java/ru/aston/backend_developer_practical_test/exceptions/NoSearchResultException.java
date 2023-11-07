package ru.aston.backend_developer_practical_test.exceptions;

public class NoSearchResultException extends RuntimeException {
    public NoSearchResultException(String message) {
        super(message);
    }
}
