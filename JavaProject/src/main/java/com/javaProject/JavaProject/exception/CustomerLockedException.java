package com.javaProject.JavaProject.exception;



public class CustomerLockedException extends RuntimeException {
    public CustomerLockedException(String message) {
        super(message);
    }
}
