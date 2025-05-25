package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.RoomService;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomAvailableStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;

    @GetMapping("room")
    public List<DetailedRoomDto> getAllRooms() {
        log.info("Get all rooms");
        return roomService.getAllDetailedRooms();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<DetailedRoomDto> getRoomById(@PathVariable Long id) {
        Optional<DetailedRoomDto> dto = roomService.getDetailedRoomById(id);
        log.info("Get room by id: {}", id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); //mappa om till en responseEntity
    }

    @DeleteMapping("room/{id}/delete")
    public String deleteRoom (@PathVariable Long id) {
        log.info("Delete room by id: {}", id);
        return roomService.deleteRoom(id);
    }

    @PostMapping("room/add")
    public String addRoom(@RequestBody @Valid DetailedRoomDto room) {
        log.info("Added new room with id: {}", room.getId());
        return roomService.addRoom(room);
    }

    @PutMapping("room/{id}/update")
    public String updateRoom(@PathVariable Long id, @RequestBody DetailedRoomDto roomDto) { //ingen @Valid behövs
        log.info("Update room with id: {}", id);
        return roomService.updateRoom(id, roomDto);
    }

    //http://localhost:8080/room/search?checkIn=2025-05-20&checkOut=2025-05-28&guests=4
    @GetMapping("room/search")
    public List<RoomAvailableStat> searchAvailableRooms(@RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                                        @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                                                        @RequestParam("guests") int numberOfGuests) {
        return roomService.searchAvailableRooms(checkIn, checkOut, numberOfGuests);
    }

}
