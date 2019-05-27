package com.hotel.booking.exception;

public abstract class HrsException extends RuntimeException {

    public HrsException() {}

    public abstract String getErrorCode();

    HrsException(String message) {
        super(message);
    }

    public HrsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HrsException(Throwable cause) {
        super(cause);
    }

    public HrsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
