package org.example.hotel_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "agency")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_name", nullable = false)
    private String agencyName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
