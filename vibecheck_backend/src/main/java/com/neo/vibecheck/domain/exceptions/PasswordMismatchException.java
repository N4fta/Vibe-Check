package com.neo.vibecheck.domain.exceptions;

public class PasswordMismatchException extends Exception {
    public PasswordMismatchException(String errorMessage) {
        super(errorMessage);
    }

    public PasswordMismatchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
