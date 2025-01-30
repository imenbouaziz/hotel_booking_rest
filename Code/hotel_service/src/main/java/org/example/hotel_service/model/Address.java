package org.example.hotel_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "position_gps", nullable = true)
    private String positionGps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPositionGps() {
        return positionGps;
    }

    public void setPositionGps(String positionGps) {
        this.positionGps = positionGps;
    }
}

