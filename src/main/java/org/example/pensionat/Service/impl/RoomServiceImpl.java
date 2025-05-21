package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Room;
import org.example.pensionat.repository.RoomRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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
    public Room dtoDetailedRoomToEntityRoom(DetailedRoomDto r) {
        return Room.builder()
                .id(r.getId())
                .roomNumber(r.getRoomNumber())
                .type(r.getRoomType())
                .baseCapacity(r.getBaseCapacity())
                .maxExtraBeds(r.getMaxExtraBeds())
                .build(); // inga myBookings skapas!
    }

    @Override
    public List<DetailedRoomDto> getAllDetailedRooms() {
        return roomRepository.findAll().stream().map(room -> entityRoomToDetailedRoomDto(room)).toList();
    }

    @Override
    public Optional<DetailedRoomDto> getDetailedRoomById(Long id) {
        return roomRepository.findById(id)
                .map(room -> entityRoomToDetailedRoomDto(room));
    }

    @Override
    public String deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            if (room.getMyBookings() != null && !room.getMyBookings().isEmpty()) {
                return "Cannot delete room with id " + id + " because it has existing bookings.";
            }

            roomRepository.deleteById(id);
            return "Room with id " + id + " was deleted";
        } else {
            return "Room with id " + id + " does not exist";
        }
    }

    @Override
    public String addRoom(DetailedRoomDto roomDto) {
        if (roomRepository.existsByRoomNumber(roomDto.getRoomNumber())) {
            return "Room with number " + roomDto.getRoomNumber() + " already exists.";
        }

        Room room = dtoDetailedRoomToEntityRoom(roomDto);
        roomRepository.save(room);
        return "Room with number " + room.getRoomNumber() + " was successfully added.";
    }

    @Override
    public String updateRoom(Long id, DetailedRoomDto updatedRoomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            return "Room with id " + id + " not found.";
        }

        Room existingRoom = optionalRoom.get();

        existingRoom.setRoomNumber(updatedRoomDto.getRoomNumber());
        existingRoom.setType(updatedRoomDto.getRoomType());
        existingRoom.setBaseCapacity(updatedRoomDto.getBaseCapacity());
        existingRoom.setMaxExtraBeds(updatedRoomDto.getMaxExtraBeds());

        roomRepository.save(existingRoom);
        return "Room with id " + id + " was successfully updated.";
    }
}
