package org.example.hotel_service.service;

import org.example.hotel_service.model.Hotel;
import org.example.hotel_service.model.Offer;
import org.example.hotel_service.dto.OfferDTO;
import org.example.hotel_service.model.Room;
import org.example.hotel_service.repository.HotelRepository;
import org.example.hotel_service.repository.OfferRepository;
import org.example.hotel_service.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public List<OfferDTO> findAvailableOffersForHotel(
            Long hotelId,
            String city,
            Integer stars,
            String category,
            Integer capacity,
            LocalDate start,
            LocalDate end,
            Double maxPrice
    ) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        if (!hotel.getAddress().getCity().equalsIgnoreCase(city) ||
                hotel.getStarsNb() < stars ||
                !hotel.getHotelCategory().equalsIgnoreCase(category)) {
            throw new IllegalArgumentException("Hotel criteria mismatch");
        }

        int totalRoomCapacity = hotel.getRooms().stream().mapToInt(Room::getCapacity).sum();

        if (totalRoomCapacity < capacity) {
            throw new IllegalArgumentException("Insufficient hotel capacity");
        }

        List<Offer> availableOffers = hotel.getRooms().stream()
                .flatMap(room -> offerRepository.findByRoomId(room.getId()).stream())
                .filter(offer -> {
                    double discountedPrice = offer.getRoom().getPricePerNight() *
                            (1 - offer.getPercentage() / 100);
                    offer.setNewPrice(discountedPrice);

                    return discountedPrice <= maxPrice &&
                            availabilityRepository.findByRoomIdAndStartAvailabilityBeforeAndEndAvailabilityAfter(
                                            offer.getRoom().getId(), end, start).stream()
                                    .anyMatch(period -> !period.isBooked());
                })
                .collect(Collectors.toList());

        return availableOffers.stream().map(offer -> new OfferDTO(
                offer.getId(),
                hotel.getId(),
                offer.getHotel().getHotelName(),
                offer.getHotel().getStarsNb(),
                offer.getRoom().getId(),
                offer.getAgency().getId(),
                offer.getAgency().getAgencyName(),
                offer.getRoom().getCapacity(),
                offer.getPercentage(),
                offer.getNewPrice(),
                offer.getRoom().getImageUrls()
        )).collect(Collectors.toList());
    }
}
