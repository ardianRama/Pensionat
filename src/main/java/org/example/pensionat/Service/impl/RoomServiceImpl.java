package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.RoomRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
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
    public RoomDto entityRoomToRoomDto(Room r) {
        return RoomDto.builder().id(r.getId()).build();
    }

    @Override
    public Room dtoDetailedRoomToEntityRoom(DetailedRoomDto r) {
        return Room.builder()
                .id(r.getId())
                .roomNumber(r.getRoomNumber())
                .type(r.getRoomType())
                .baseCapacity(r.getBaseCapacity())
                .maxExtraBeds(r.getMaxExtraBeds())
                .build(); // inga myBookings
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

        String validationError = validateRoomData(roomDto);
        if (validationError != null) {
            return validationError;
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

        boolean hasActiveBookings = existingRoom.getMyBookings().stream()
                .anyMatch(booking ->
                        booking.getCheckIn().isBefore(LocalDate.now().plusDays(1)) &&
                                booking.getCheckOut().isAfter(LocalDate.now().minusDays(1))
                );

        if (hasActiveBookings) {
            return "Room with id " + id + " cannot be updated because it has active bookings.";
        }

        String validationError = validateRoomData(updatedRoomDto);
        if (validationError != null) {
            return validationError;
        }

        if (updatedRoomDto.getRoomNumber() != 0) {
            existingRoom.setRoomNumber(updatedRoomDto.getRoomNumber());
        }
        if (updatedRoomDto.getRoomType() != null) {
            existingRoom.setType(updatedRoomDto.getRoomType());
        }
        if (updatedRoomDto.getBaseCapacity() != 0) {
            existingRoom.setBaseCapacity(updatedRoomDto.getBaseCapacity());
        }
        if (updatedRoomDto.getMaxExtraBeds() != 0) {
            existingRoom.setMaxExtraBeds(updatedRoomDto.getMaxExtraBeds());
        }

        roomRepository.save(existingRoom);
        return "Room with id " + id + " was successfully updated.";
    }

    @Override
    public String validateRoomData(DetailedRoomDto roomDto) {
        RoomType type = roomDto.getRoomType();
        int extraBeds = roomDto.getMaxExtraBeds();
        int baseCapacity = roomDto.getBaseCapacity();

        if (type == RoomType.SINGLEROOM) {
            if (extraBeds != 0) {
                return "Single rooms cannot have extra beds.";
            }
            if (baseCapacity != 1) {
                return "Single rooms must have a base capacity of 1.";
            }
        }

        if (type == RoomType.DOUBLEROOM) {
            if (extraBeds < 0 || extraBeds > 2) {
                return "Double rooms can only have 0 to 2 extra beds.";
            }
            if (baseCapacity != 2) {
                return "Double rooms must have a base capacity of 2.";
            }
        }

        return null;
    }

}
