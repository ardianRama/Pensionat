package org.example.pensionat.service;

import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomAvailableStat;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    public DetailedRoomDto entityRoomToDetailedRoomDto(Room room);

    public RoomDto entityRoomToRoomDto(Room room);

    public RoomAvailableStat entityRoomToRoomAvailableStatDto(Room room);

    public Room dtoDetailedRoomToEntityRoom(DetailedRoomDto roomDto);

    public List<DetailedRoomDto> getAllDetailedRooms();

    public Optional<DetailedRoomDto> getDetailedRoomById(Long id);

    public String deleteRoom(Long id);

    public String addRoom(DetailedRoomDto room);

    public String updateRoom(Long id, DetailedRoomDto updatedRoomDto);

    public String validateRoomData(DetailedRoomDto roomDto);

    public List<RoomAvailableStat> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut, int numberOfGuests);
}
