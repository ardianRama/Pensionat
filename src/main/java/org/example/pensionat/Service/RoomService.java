package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    public DetailedRoomDto entityRoomToDetailedRoomDto(Room room);

    public RoomDto entityRoomToRoomDto(Room room);

    //komplett med booking, behövs nog inte
    //public Room dtoDetailedRoomToEntityRoom(DetailedRoomDto room);

    //Ingen booking
    public Room dtoDetailedRoomToEntityRoom(DetailedRoomDto roomDto);

    public List<DetailedRoomDto> getAllDetailedRooms();

    public Optional<DetailedRoomDto> getDetailedRoomById(Long id);

    public String deleteRoom(Long id);

    public String addRoom(DetailedRoomDto room);

    public String updateRoom(Long id, DetailedRoomDto updatedRoomDto);

    public String validateRoomData(DetailedRoomDto roomDto);
}
