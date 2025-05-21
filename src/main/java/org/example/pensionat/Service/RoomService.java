package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Room;

import java.util.List;

public interface RoomService {

    public DetailedRoomDto entityRoomToDetailedRoomDto(Room room);

    public List<DetailedRoomDto> getAllDetailedRooms();

}
