package org.example.hotel_service.repository;

import org.example.hotel_service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}

