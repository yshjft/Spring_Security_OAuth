package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception;

public class UnAuthorizedAccessException extends RuntimeException{
    public UnAuthorizedAccessException() {
    }

    public UnAuthorizedAccessException(String message) {
        super(message);
    }

    public UnAuthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedAccessException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizedAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
