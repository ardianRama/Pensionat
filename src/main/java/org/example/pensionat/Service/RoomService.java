package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Room;

public interface RoomService {

    public DetailedRoomDto entityRoomToDetailedRoomDto(Room room);

}
