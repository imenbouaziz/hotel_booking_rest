package org.example.hotel_service.model;


import jakarta.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "percentage", nullable = false)
    private double percentage;

    @Column(name = "new_price", nullable = false)
    private double newPrice;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
