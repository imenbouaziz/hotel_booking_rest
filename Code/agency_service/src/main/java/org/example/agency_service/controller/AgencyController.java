package org.example.agency_service.controller;

import org.example.agency_service.exceptions.ForbiddenAccessException;
import org.example.agency_service.exceptions.ResourceNotFoundException;
import org.example.agency_service.exceptions.UnauthorizedAccessException;
import org.example.agency_service.model.Agency;
import org.example.agency_service.model.Offer;
import org.example.agency_service.service.AuthenticationService;
import org.example.agency_service.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agency")
@CrossOrigin(origins = "http://localhost:3000")
public class AgencyController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{agencyId}/offers")
    public ResponseEntity<List<Offer>> getOffersForAgency(
            @PathVariable Long agencyId,
            @RequestParam String city,
            @RequestParam Integer stars,
            @RequestParam String category,
            @RequestParam Integer capacity,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam Double maxPrice,
            @RequestParam String agencyLogin,
            @RequestParam String agencyPassword) {

        try {
            //1- Authenticate agency
            Agency agency = authenticationService.authenticateAgency(agencyLogin, agencyPassword)
                    .orElseThrow(() -> new UnauthorizedAccessException("Invalid login or password."));

            //2- Check if the agency ID matches
            if (!agency.getId().equals(agencyId)) {
                throw new ForbiddenAccessException("Agency ID does not match the authenticated user.");
            }

            //3- Fetch offers for the authenticated agency
            List<Offer> offers = offerService.getOffersForAgency(agency, city, stars, category, capacity, start, end, maxPrice);

            if (offers.isEmpty()) {
                throw new ResourceNotFoundException("No offers found matching the given criteria.");
            }

            return ResponseEntity.ok(offers);
        } catch (UnauthorizedAccessException | ForbiddenAccessException | ResourceNotFoundException e) {
            //4- Handle known exceptions and return appropriate HTTP response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            //5-  Handle unexpected errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
