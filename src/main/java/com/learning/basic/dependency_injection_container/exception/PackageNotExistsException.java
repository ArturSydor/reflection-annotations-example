package com.learning.basic.dependency_injection_container.exception;

public class PackageNotExistsException extends RuntimeException {
    public PackageNotExistsException(String message) {
        super(message);
    }
}
