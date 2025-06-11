package com.xcoder.redis.clone.exception;

public class ReaderException extends RuntimeException {
    public ReaderException() {
    }

    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderException(Throwable cause) {
        super(cause);
    }

    public ReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
