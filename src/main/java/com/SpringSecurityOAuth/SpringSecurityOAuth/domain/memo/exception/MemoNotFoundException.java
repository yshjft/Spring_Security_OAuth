package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception;

public class MemoNotFoundException extends RuntimeException{
    public MemoNotFoundException() {
    }

    public MemoNotFoundException(String message) {
        super(message);
    }

    public MemoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoNotFoundException(Throwable cause) {
        super(cause);
    }

    public MemoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
