package org.example.hotel_service.repository;

import org.example.hotel_service.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByRoomId(Long roomId);
}
