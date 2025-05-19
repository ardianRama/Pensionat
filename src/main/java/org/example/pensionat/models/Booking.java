package org.example.pensionat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Booking {

    @Id @GeneratedValue
    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int extraBeds;

    private int numberOfGuests;

    @JoinColumn @ManyToOne
    private Customer customer;

    @JoinColumn @ManyToOne
    private Room room;

    public Booking(LocalDate checkIn, LocalDate checkOut, int extraBeds, int numberOfGuests, Customer customer, Room room) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.extraBeds = extraBeds;
        this.numberOfGuests = numberOfGuests;
        this.customer = customer;
        this.room = room;
    }
}
