package com.androidtest.minderatest.exception;

public class UnsupportedInitializationException extends RuntimeException {

    public UnsupportedInitializationException() {
        super("Use instance() method instead");
    }

}
