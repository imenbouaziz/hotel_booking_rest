package org.example.hotel_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(name = "client_fname", nullable = false)
    private String clientFname;

    @Column(name = "client_lname", nullable = false)
    private String clientLname;

    @Column(name = "client_age", nullable = false)
    private int clientAge;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getClientFname() {
        return clientFname;
    }

    public void setClientFname(String clientFname) {
        this.clientFname = clientFname;
    }

    public String getClientLname() {
        return clientLname;
    }

    public void setClientLname(String clientLname) {
        this.clientLname = clientLname;
    }

    public int getClientAge() {
        return clientAge;
    }

    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
