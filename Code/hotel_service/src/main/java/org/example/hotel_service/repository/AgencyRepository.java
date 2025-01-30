package org.example.hotel_service.repository;

import org.example.hotel_service.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Optional<Agency> findByLogin(String login);
}
