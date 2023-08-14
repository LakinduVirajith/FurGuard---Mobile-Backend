package com.furguard.backend.exception;

public class PreconditionRequiredException extends Exception{
    public PreconditionRequiredException() {
        super();
    }

    public PreconditionRequiredException(String message) {
        super(message);
    }

    public PreconditionRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionRequiredException(Throwable cause) {
        super(cause);
    }

    protected PreconditionRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
