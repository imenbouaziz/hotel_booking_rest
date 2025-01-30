package org.example.hotel_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private Room room;

    @Column(name = "start_availability", nullable = false)
    private LocalDate startAvailability;

    @Column(name = "end_availability", nullable = false)
    private LocalDate endAvailability;

    @Column(name = "is_booked", nullable = false)
    private boolean isBooked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartAvailability() {
        return startAvailability;
    }

    public void setStartAvailability(LocalDate startAvailability) {
        this.startAvailability = startAvailability;
    }

    public LocalDate getEndAvailability() {
        return endAvailability;
    }

    public void setEndAvailability(LocalDate endAvailability) {
        this.endAvailability = endAvailability;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
