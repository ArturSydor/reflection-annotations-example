package com.learning.basic.dependency_injection_container.exception;

public class ContextInitializationException extends RuntimeException {
    public ContextInitializationException(String message) {
        super(message);
    }

    public ContextInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
