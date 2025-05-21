package org.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.RoomType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRoomDto {

    private Long id;
    private int roomNumber;
    private RoomType roomType;
    private int baseCapacity;
    private int maxExtraBeds;
    private List<BookingDto> myBookings;
}
