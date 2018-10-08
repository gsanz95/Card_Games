package com.company;

/**
 * Raised when a deck class is empty
 */
public class EmptyDeckException extends RuntimeException {

    public EmptyDeckException(String message) {
        super(message);
    }
}
