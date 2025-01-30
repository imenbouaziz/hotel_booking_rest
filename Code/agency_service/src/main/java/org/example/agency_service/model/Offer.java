package org.example.agency_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class Offer implements Serializable {
    @Id
    private Long id;
    private Long hotelId;
    private String hotelName;
    private Integer hotelStars;
    private Long agencyId;
    private String agencyName; // New field for agency name
    private Long roomId;
    private Double percentage;
    private Double newPrice;
    private List<String> roomImageUrls;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(Integer hotelStars) {
        this.hotelStars = hotelStars;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public List<String> getRoomImageUrls() {
        return roomImageUrls;
    }

    public void setRoomImageUrls(List<String> roomImageUrls) {
        this.roomImageUrls = roomImageUrls;
    }

    @Override
    public String toString() {
        return "Offer{id=" + id + ", hotelId=" + hotelId + ", hotelName='" + hotelName + '\'' +
                ", hotelStars=" + hotelStars + ", agencyId=" + agencyId + ", agencyName='" + agencyName + '\'' +
                ", roomId=" + roomId + ", percentage=" + percentage + ", newPrice=" + newPrice +
                ", roomImageUrls=" + roomImageUrls + "}";
    }
}
