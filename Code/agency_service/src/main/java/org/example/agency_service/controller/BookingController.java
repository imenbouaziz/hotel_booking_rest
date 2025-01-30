package org.example.agency_service.controller;

import org.example.agency_service.exceptions.InvalidBookingDataException;
import org.example.agency_service.exceptions.ResourceNotFoundException;
import org.example.agency_service.exceptions.UnauthorizedAccessException;
import org.example.agency_service.model.Booking;
import org.example.agency_service.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/agencies")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{agencyId}/booking/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createBooking(
            @PathVariable Long agencyId,
            @RequestParam Long offerId,
            @RequestParam Long clientId,
            @RequestParam String clientFname,
            @RequestParam String clientLname,
            @RequestParam int clientAge,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String cardNumber,
            @RequestParam String cardHolder,
            @RequestParam String expirationDate,
            @RequestParam String cvv) {

        try {
            //1- Create the booking via the service
            Booking booking = bookingService.createBooking(
                    offerId, clientId, clientFname, clientLname, clientAge, startDate,
                    endDate, agencyId, cardNumber, cardHolder, expirationDate, cvv
            );

            //2- Return the created booking as a JSON response
            return ResponseEntity.ok(booking);
        } catch (InvalidBookingDataException e) {
            //3- Return JSON response for invalid inputs
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid booking data");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (ResourceNotFoundException e) {
            //4- Return JSON response for not found resources
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Resource not found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (UnauthorizedAccessException e) {
            //5- Return JSON response for unauthorized access
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized access");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            //6- Handle unexpected errors
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Server error");
            errorResponse.put("message", "An unexpected error occurred. Please try again later.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
