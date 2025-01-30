package org.example.hotel_service.controller;

import org.example.hotel_service.exception.InvalidBookingDataException;
import org.example.hotel_service.exception.ResourceNotFoundException;
import org.example.hotel_service.exception.UnauthorizedAccessException;
import org.example.hotel_service.model.Agency;
import org.example.hotel_service.model.Booking;
import org.example.hotel_service.service.AuthenticationService;
import org.example.hotel_service.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
public class BookingController {

    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    public BookingController(BookingService bookingService, AuthenticationService authenticationService) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{hotelId}/booking/create")
    public ResponseEntity<?> createBooking(
            @PathVariable Long hotelId,
            @RequestParam Long offerId,
            @RequestParam String clientFname,
            @RequestParam String clientLname,
            @RequestParam int clientAge,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String agencyLogin,
            @RequestParam String agencyPassword) {

        try {
            //1- Authenticating the agency
            Optional<Agency> agency = authenticationService.authenticateAgency(agencyLogin, agencyPassword);
            if (agency.isEmpty()) {
                throw new UnauthorizedAccessException("Invalid agency credentials.");
            }

            //2- Creating the booking via the service
            Booking booking = bookingService.createBooking(
                    hotelId, offerId, clientFname, clientLname, clientAge, startDate, endDate);

            //3- Returning the created booking as a JSON response
            return ResponseEntity.ok(booking);

        } catch (UnauthorizedAccessException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized access");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(401).body(errorResponse);

        } catch (InvalidBookingDataException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid booking data");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);

        } catch (ResourceNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Resource not found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Server error");
            errorResponse.put("message", "An unexpected error occurred. Please try again later.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
