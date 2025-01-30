package org.example.hotel_service.exception;

public class InvalidBookingDataException extends RuntimeException {
    public InvalidBookingDataException(String message) {
        super(message);
    }
}
