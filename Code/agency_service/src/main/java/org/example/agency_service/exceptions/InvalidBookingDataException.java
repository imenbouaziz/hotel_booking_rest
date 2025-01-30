package org.example.agency_service.exceptions;

public class InvalidBookingDataException extends RuntimeException {
    public InvalidBookingDataException(String message) {
        super(message);
    }
}
