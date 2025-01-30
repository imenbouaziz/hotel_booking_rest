package org.example.hotel_service.dto;

import java.io.Serializable;
import java.util.List;

public class OfferDTO implements Serializable {
    private Long id;
    private Long hotelId;
    private String hotelName;
    private Integer hotelStars;
    private Long roomId;
    private Long agencyId;
    private String agencyName;
    private Integer capacity;
    private Double percentage;
    private Double newPrice;
    private List<String> roomImageUrls;

    public OfferDTO() {
    }

    public OfferDTO(Long id, Long hotelId, String hotelName, Integer hotelStars, Long roomId, Long agencyId,
                    String agencyName, Integer capacity, Double percentage, Double newPrice, List<String> roomImageUrls) {
        this.id = id;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelStars = hotelStars;
        this.roomId = roomId;
        this.agencyId = agencyId;
        this.agencyName = agencyName;  // Set agency name
        this.capacity = capacity;
        this.percentage = percentage;
        this.newPrice = newPrice;
        this.roomImageUrls = roomImageUrls;  // Set room images
    }


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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
        return "OfferDTO{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", hotelStars=" + hotelStars +
                ", roomId=" + roomId +
                ", agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", capacity=" + capacity +
                ", percentage=" + percentage +
                ", newPrice=" + newPrice +
                ", roomImageUrls=" + roomImageUrls +
                '}';
    }
}
