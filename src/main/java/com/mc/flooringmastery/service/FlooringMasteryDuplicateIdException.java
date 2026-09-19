package com.mc.flooringmastery.service;

public class FlooringMasteryDuplicateIdException extends RuntimeException {

    public FlooringMasteryDuplicateIdException(String message) {
        super(message);
    }

    public FlooringMasteryDuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
