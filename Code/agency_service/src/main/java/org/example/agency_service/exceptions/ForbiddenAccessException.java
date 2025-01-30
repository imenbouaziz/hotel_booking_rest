package org.example.agency_service.exceptions;

public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException(String message) {
        super(message);
    }
}
