package org.example.pensionat.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pensionat.models.RoomType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBookingDto {

    private Long id;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkIn;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkOut;

    @Min(value = 0, message = "0, 1 or 2 to extra beds depending on the room type")
    @Max(value = 2, message = "0, 1 or 2 to extra beds depending on the room type")
    private int extraBeds;

    @Min(value = 1, message = "Minimum 1 guest")
    @Max(value = 4, message = "Up to 4 guest depending on room type and size")
    private int numberOfGuests;

    @NotNull(message = "Customer is required")
    private CustomerDto customer;

    @NotNull(message = "Room is required")
    private RoomDto room;

    //private RoomType type;
}
