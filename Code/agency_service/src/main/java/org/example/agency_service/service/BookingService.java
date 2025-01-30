package org.example.agency_service.service;

import org.example.agency_service.model.*;
import org.example.agency_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;
    private final AgencyRepository agencyRepository;
    private final CardInfoRepository cardInfoRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, OfferRepository offerRepository,
                          ClientRepository clientRepository, RestTemplate restTemplate,
                          AgencyRepository agencyRepository, CardInfoRepository cardInfoRepository) {
        this.bookingRepository = bookingRepository;
        this.offerRepository = offerRepository;
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
        this.agencyRepository = agencyRepository;
        this.cardInfoRepository = cardInfoRepository;
    }

    public Booking createBooking(Long offerId, Long clientId, String clientFname, String clientLname,
                                 int clientAge, LocalDate startDate, LocalDate endDate, Long agencyId,
                                 String cardNumber, String cardHolder, String expirationDate, String cvv) {
        try {
            //1- Fetch Offer and Client
            Offer offer = offerRepository.findById(offerId)
                    .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));
            System.out.println("Offer fetched: " + offer);

            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Client with ID " + clientId + " not found"));
            System.out.println("Client fetched: " + client);

            //2- Retrieve Agency Login and Password by id
            Agency agency = agencyRepository.findById(agencyId)
                    .orElseThrow(() -> new IllegalArgumentException("Agency with ID " + agencyId + " not found"));

            String agencyLogin = agency.getLogin();
            String agencyPassword = agency.getPassword();
            System.out.println("Agency login retrieved: " + agencyLogin);

            //3- Calculate the number of nights
            long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);

            //4- Calculate total price
            double newPrice = offer.getNewPrice();
            double totalPrice = newPrice * numberOfNights;

            //5- Create and Save Local Booking
            Booking booking = new Booking();
            booking.setOffer(offer);
            booking.setClient(client);
            booking.setClientFname(clientFname);
            booking.setClientLname(clientLname);
            booking.setClientAge(clientAge);
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setTotalPrice(totalPrice);

            Booking savedBooking = bookingRepository.save(booking);
            System.out.println("Local booking saved: " + savedBooking);

            //6- Check if card number already exists
            CardInfo existingCardInfo = cardInfoRepository.findByCardNumber(cardNumber);
            if (existingCardInfo == null) {
                //If card number does not exist, save new card info
                CardInfo cardInfo = new CardInfo();
                cardInfo.setCardNumber(cardNumber);
                cardInfo.setCardHolder(cardHolder);
                cardInfo.setExpirationDate(expirationDate);
                cardInfo.setCvv(cvv);
                cardInfo.setClient(client);

                CardInfo savedCardInfo = cardInfoRepository.save(cardInfo);
                System.out.println("Card info saved: " + savedCardInfo);
            } else {
                System.out.println("Card number already exists: " + cardNumber);
            }

            //7- Call Hotel Service API to create a booking at the hotel
            Long hotelId = offer.getHotelId();
            if (hotelId == null) {
                throw new IllegalStateException("Hotel ID is missing for offer: " + offerId);
            }

            String hotelServiceUrl = "http://localhost:8080/api/hotels/{hotelId}/booking/create?" +
                    "offerId=" + offerId + "&" +
                    "clientFname=" + clientFname + "&" +
                    "clientLname=" + clientLname + "&" +
                    "clientAge=" + clientAge + "&" +
                    "startDate=" + startDate + "&" +
                    "endDate=" + endDate + "&" +
                    "agencyLogin=" + agencyLogin + "&" +
                    "agencyPassword=" + agencyPassword;

            ResponseEntity<Booking> response = restTemplate.postForEntity(hotelServiceUrl, null, Booking.class, hotelId);

            System.out.println("Hotel service response: " + response);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Booking successfully created on hotel side: " + response.getBody());
            } else {
                System.err.println("Failed to create booking on hotel side. Status: " + response.getStatusCode());
            }

            return savedBooking;

        } catch (Exception e) {
            System.err.println("Exception in createBooking: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating booking", e);
        }
    }

}
