package org.example.agency_service.repository;

import org.example.agency_service.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Optional<Agency> findByLoginAndPassword(String login, String password);
    Optional<Agency> findById(Long id);
}
