package org.example.agency_service.repository;

import org.example.agency_service.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
