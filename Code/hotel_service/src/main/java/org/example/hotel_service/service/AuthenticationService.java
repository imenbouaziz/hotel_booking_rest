package org.example.hotel_service.service;

import org.example.hotel_service.model.Agency;
import org.example.hotel_service.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final AgencyRepository agencyRepository;

    @Autowired
    public AuthenticationService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public Optional<Agency> authenticateAgency(String login, String password) {

        Optional<Agency> agency = agencyRepository.findByLogin(login);
        if (agency.isPresent() && agency.get().getPassword().equals(password)) {
            return agency;
        }
        return Optional.empty();
    }
}
