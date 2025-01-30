package org.example.agency_service.service;

import org.example.agency_service.model.Agency;
import org.example.agency_service.repository.AgencyRepository;
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

    /**
     * Authenticate an agency based on its login and password
     *
     * @param agencyLogin    the login
     * @param agencyPassword the password
     */
    public Optional<Agency> authenticateAgency(String agencyLogin, String agencyPassword) {
        Agency agency = findAgencyByLoginAndPassword(agencyLogin, agencyPassword);
        return Optional.ofNullable(agency);
    }

    /**
     * Find an agency based on login and password
     * @param login the login (username) of the agency
     * @param password the password of the agency
     */
    private Agency findAgencyByLoginAndPassword(String login, String password) {
        Optional<Agency> agencyOptional = agencyRepository.findByLoginAndPassword(login, password);
        return agencyOptional.orElse(null);
    }
}
