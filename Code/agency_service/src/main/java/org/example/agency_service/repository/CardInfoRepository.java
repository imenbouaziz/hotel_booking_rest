package org.example.agency_service.repository;

import org.example.agency_service.model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
    CardInfo findByCardNumber(String cardNumber);
}
