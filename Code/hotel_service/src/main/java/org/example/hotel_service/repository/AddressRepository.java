package org.example.hotel_service.repository;

import org.example.hotel_service.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Offer, Long> {}