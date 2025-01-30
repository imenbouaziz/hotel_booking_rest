package org.example.hotel_service.controller;

import org.example.hotel_service.exception.UnauthorizedAccessException;
import org.example.hotel_service.exception.InvalidOfferSearchCriteriaException;
import org.example.hotel_service.dto.OfferDTO;
import org.example.hotel_service.service.AvailabilityService;
import org.example.hotel_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{hotelId}/availability/offers")
    public ResponseEntity<?> getAvailableOffersForHotel(
            @PathVariable Long hotelId,
            @RequestParam String city,
            @RequestParam Integer stars,
            @RequestParam String category,
            @RequestParam Integer capacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam Double maxPrice,
            @RequestParam String agencyLogin,
            @RequestParam String agencyPassword) {

        try {
            //1- Authenticating the agency
            if (authenticationService.authenticateAgency(agencyLogin, agencyPassword).isEmpty()) {
                throw new UnauthorizedAccessException("Invalid agency credentials.");
            }

            //2- Fetching available offers
            List<OfferDTO> offers = availabilityService.findAvailableOffersForHotel(
                    hotelId, city, stars, category, capacity, start, end, maxPrice);

            if (offers.isEmpty()) {
                throw new InvalidOfferSearchCriteriaException("No offers found for the provided criteria.");
            }

            //3- Return the list of offers
            return ResponseEntity.ok(offers);

        } catch (UnauthorizedAccessException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized access");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(401).body(errorResponse);

        } catch (InvalidOfferSearchCriteriaException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No offers found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(400).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Server error");
            errorResponse.put("message", "An unexpected error occurred. Please try again later.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
