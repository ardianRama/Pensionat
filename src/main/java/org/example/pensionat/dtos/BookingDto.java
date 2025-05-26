package org.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private RoomDto room;

    private LocalDate checkIndto;

    private LocalDate checkOut;

    private int extraBeds;

    private int numberOfGuests;

    //private Customer customer;
}
