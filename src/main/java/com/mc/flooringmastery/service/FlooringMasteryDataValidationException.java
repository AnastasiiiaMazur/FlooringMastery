package com.mc.flooringmastery.service;

public class FlooringMasteryDataValidationException extends RuntimeException {

    public FlooringMasteryDataValidationException(String message) {
        super(message);
    }

    public FlooringMasteryDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
