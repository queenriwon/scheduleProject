package com.example.scheduleproject.exception;

public class NoMatchPasswordConfirmation extends RuntimeException {
    public NoMatchPasswordConfirmation(String message) {
        super(message);
    }
}
