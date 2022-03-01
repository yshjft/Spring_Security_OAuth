package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception;

public class MemoAccessDeniedException extends RuntimeException{
    public MemoAccessDeniedException() {
    }

    public MemoAccessDeniedException(String message) {
        super(message);
    }

    public MemoAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public MemoAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
