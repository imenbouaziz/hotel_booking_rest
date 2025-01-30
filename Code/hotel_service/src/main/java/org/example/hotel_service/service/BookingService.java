package org.example.hotel_service.service;

import org.example.hotel_service.model.Booking;
import org.example.hotel_service.model.Offer;
import org.example.hotel_service.model.Room;
import org.example.hotel_service.model.Availability;
import org.example.hotel_service.repository.BookingRepository;
import org.example.hotel_service.repository.OfferRepository;
import org.example.hotel_service.repository.RoomRepository;
import org.example.hotel_service.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final OfferRepository offerRepository;
    private final RoomRepository roomRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, OfferRepository offerRepository,
                          RoomRepository roomRepository, AvailabilityRepository availabilityRepository) {
        this.bookingRepository = bookingRepository;
        this.offerRepository = offerRepository;
        this.roomRepository = roomRepository;
        this.availabilityRepository = availabilityRepository;
    }

    /**
     * Create a booking for a given hotel, offer, and client details.
     * @param hotelId Hotel ID where the booking is being made
     * @param offerId Offer ID chosen for the booking
     * @param clientFname First name of the client
     * @param clientLname Last name of the client
     * @param clientAge Age of the client
     * @param startDate Start date of the booking
     * @param endDate End date of the booking
     */
    public Booking createBooking(Long hotelId, Long offerId, String clientFname, String clientLname,
                                 int clientAge, LocalDate startDate, LocalDate endDate) {
        //1- Retrieving the offer using offerId
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));

        //2- Checking if the offer belongs to the correct hotel
        if (!offer.getHotel().getId().equals(hotelId)) {
            throw new IllegalArgumentException("Offer does not belong to the specified hotel.");
        }

        //3- Getting the room associated with the offer
        Room room = offer.getRoom();

        //4- Calculating the number of nights
        long numberOfNights = startDate.until(endDate).getDays();

        //5- Calculating the discounted price per night
        double discountedPrice = room.getPricePerNight() - (room.getPricePerNight() * offer.getPercentage() / 100);

        //6- Calculating the total price for the booking
        double totalPrice = discountedPrice * numberOfNights;

        //7- Checking if the room is available for the given dates
        List<Availability> availablePeriods = availabilityRepository.findByRoomIdAndStartAvailabilityBeforeAndEndAvailabilityAfter(
                room.getId(), endDate, startDate);

        boolean isAvailable = availablePeriods.stream().anyMatch(period ->
                (period.getStartAvailability().isBefore(startDate) || period.getStartAvailability().isEqual(startDate)) &&
                        (period.getEndAvailability().isAfter(endDate) || period.getEndAvailability().isEqual(endDate)) &&
                        !period.isBooked());

        if (!isAvailable) {
            throw new IllegalArgumentException("Room is not available for the selected dates.");
        }

        //8- Creation of a new booking object
        Booking booking = new Booking();
        booking.setOffer(offer);
        booking.setClientFname(clientFname);
        booking.setClientLname(clientLname);
        booking.setClientAge(clientAge);
        booking.setTotalPrice(totalPrice);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);

        Booking confirmedBooking = bookingRepository.save(booking);

        //9- Marking the availability as booked (it is turned to 1 not 0)
        Availability availability = availablePeriods.get(0);
        availability.setBooked(true);
        availabilityRepository.save(availability);

        //10- Splitting the availability periods si necessaire
        if (availability.getStartAvailability().isBefore(startDate)) {
            Availability newAvailabilityBefore = new Availability();
            newAvailabilityBefore.setRoom(room);
            newAvailabilityBefore.setStartAvailability(availability.getStartAvailability());
            newAvailabilityBefore.setEndAvailability(startDate.minusDays(1));
            newAvailabilityBefore.setBooked(false);
            availabilityRepository.save(newAvailabilityBefore);
        }

        if (availability.getEndAvailability().isAfter(endDate)) {
            Availability newAvailabilityAfter = new Availability();
            newAvailabilityAfter.setRoom(room);
            newAvailabilityAfter.setStartAvailability(endDate.plusDays(1));
            newAvailabilityAfter.setEndAvailability(availability.getEndAvailability());
            newAvailabilityAfter.setBooked(false);
            availabilityRepository.save(newAvailabilityAfter);
        }

        return confirmedBooking;
    }
}
