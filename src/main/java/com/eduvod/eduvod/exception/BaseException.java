package com.eduvod.eduvod.exception;

public abstract class BaseException extends RuntimeException {
    protected final int status;

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}