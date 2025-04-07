package com.example.customerotpservice.exception;

public class LockedUserException extends RuntimeException {
    public LockedUserException(String message) {
        super(message);
    }
}