package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Room;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Override
    public DetailedRoomDto entityRoomToDetailedRoomDto(Room room) {
        return null;
    }
}
