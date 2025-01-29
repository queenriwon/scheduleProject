package com.example.scheduleproject.exception;

public class MissingRequestParameterException extends RuntimeException {
    public MissingRequestParameterException(String message) {
        super(message);
    }
}
