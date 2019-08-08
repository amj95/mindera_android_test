package com.androidtest.minderatest.exception;

public class ClassNotInitializedException extends RuntimeException {

    public ClassNotInitializedException() {
        super("Class not correctly initialized. Use instance() method instead");
    }

}
