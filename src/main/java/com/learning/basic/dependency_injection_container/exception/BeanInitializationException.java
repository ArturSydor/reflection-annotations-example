package com.learning.basic.dependency_injection_container.exception;

public class BeanInitializationException extends RuntimeException {
    public BeanInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
