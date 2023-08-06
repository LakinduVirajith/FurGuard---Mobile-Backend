package com.furguard.backend.errors;

public class AlreadyExistEmailException extends Exception{
    public AlreadyExistEmailException() {
        super();
    }

    public AlreadyExistEmailException(String message) {
        super(message);
    }

    public AlreadyExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistEmailException(Throwable cause) {
        super(cause);
    }

    protected AlreadyExistEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
