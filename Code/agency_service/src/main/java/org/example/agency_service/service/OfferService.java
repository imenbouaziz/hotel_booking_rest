package org.example.agency_service.service;

import org.example.agency_service.model.Agency;
import org.example.agency_service.model.Offer;
import org.example.agency_service.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OfferService {

    @Value("${hotel.service.url}")
    private String hotelServiceUrl;

    private final RestTemplate restTemplate;
    private final OfferRepository offerRepository;

    public OfferService(RestTemplate restTemplate, OfferRepository offerRepository) {
        this.restTemplate = restTemplate;
        this.offerRepository = offerRepository;
    }

    /**
     * Get offers for the agency across all hotels.
     */
    public List<Offer> getOffersForAgency(Agency agency, String city, Integer stars, String category,
                                          Integer capacity, String start, String end, Double maxPrice) {
        List<Offer> allOffers = new ArrayList<>();

        //1- Fetching offers through all hotels , looping ids
        for (Long hotelId : getAllHotelIds()) {
            String url = hotelServiceUrl + "/api/hotel/" + hotelId + "/availability/offers?"
                    + "city=" + city
                    + "&stars=" + stars
                    + "&category=" + category
                    + "&capacity=" + capacity
                    + "&start=" + start
                    + "&end=" + end
                    + "&maxPrice=" + maxPrice
                    + "&agencyLogin=" + agency.getLogin()
                    + "&agencyPassword=" + agency.getPassword();

            try {

                Offer[] offers = restTemplate.getForObject(url, Offer[].class);

                System.out.println("Response from hotel service for hotel ID " + hotelId + ": " + Arrays.toString(offers));

                System.out.println("Fetched offers for hotel " + hotelId + ": ");
                if (offers != null) {
                    for (Offer offer : offers) {
                        System.out.println("Offer fetched: " + offer);
                    }
                }

                if (offers != null) {
                    for (Offer offer : offers) {
                        //2- Checking the agencyId matches and save the offer as it is
                        if (offer.getAgencyId() != null && offer.getAgencyId().equals(agency.getId())) {
                            allOffers.add(offer);

                            //3- Saving the found offer to the agency db
                            if (!offerRepository.existsById(offer.getId())) {
                                offerRepository.save(offer);
                            }
                        }
                    }
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    //4- Handling issues like hotel criteria mismatches
                    System.out.println("Hotel " + hotelId + " does not match the criteria, skipping...");
                } else {
                    //5- Handling server errors and unknown issues
                    System.out.println("Error fetching offers for hotel " + hotelId + ": " + e.getMessage());
                }
            } catch (Exception e) {
                //6- ANy other unexpected exceptions
                System.out.println("Unexpected error for hotel " + hotelId + ": " + e.getMessage());
            }
        }

        System.out.println("Total available offers found: " + allOffers.size());
        return allOffers;
    }

    private List<Long> getAllHotelIds() {
        //List of hotel ids for the loop
        return List.of(1L, 2L);
    }
}
