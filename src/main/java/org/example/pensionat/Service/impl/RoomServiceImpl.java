package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Room;
import org.example.pensionat.repository.RoomRepository;
import org.springframework.stereotype.Service;

//..
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final BookingService bookingService;
    private final RoomRepository roomRepository;

    @Override
    public DetailedRoomDto entityRoomToDetailedRoomDto(Room r) {
        return DetailedRoomDto.builder().id(r.getId()).roomNumber(r.getRoomNumber()).roomType(r.getType())
                .baseCapacity(r.getBaseCapacity()).maxExtraBeds(r.getMaxExtraBeds()).myBookings(r.getMyBookings()
                        .stream().map(bookings -> bookingService.entityBookingToBookingDto(bookings)).toList()).build();
    }

    @Override
    public List<DetailedRoomDto> getAllDetailedRooms() {
        return roomRepository.findAll().stream().map(room -> entityRoomToDetailedRoomDto(room)).toList();
    }
}
