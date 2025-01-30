package org.example.hotel_service.repository;

import org.example.hotel_service.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByRoomIdAndStartAvailabilityBeforeAndEndAvailabilityAfter(Long roomId, LocalDate startDate, LocalDate endDate);
}
