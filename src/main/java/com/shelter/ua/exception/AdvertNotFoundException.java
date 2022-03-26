package com.shelter.ua.exception;

public class AdvertNotFoundException extends RuntimeException {
    public AdvertNotFoundException(String message) {
        super(message);
    }
}