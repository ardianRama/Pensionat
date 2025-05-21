package org.example.pensionat.controllers;

import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final RoomRepository roomRepo;

    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    //http://localhost:8080/room
    @GetMapping("room")
    public List<Room> Room() {
        log.info("Showing all the rooms");
        return roomRepo.findAll();
    }

    //http://localhost:8080/room/2
    @GetMapping("room/{id}")
    public Room getRoomById(@PathVariable Long id) {
        log.info("Showing room with id {}", id);
        return roomRepo.findById(id).get();
    }

    //http://localhost:8080/room/3/delete
    @GetMapping("room/{id}/delete")
    public String deleteRoomById(@PathVariable Long id) {
        roomRepo.deleteById(id);
        log.info("Deleted room with id {}", id);
        return "Room with id " + id + " deleted";
    }

    //http://localhost:8080/room/add?roomNumber=54&roomType=Double&baseCapacity=2&maxExtraBeds=0
    @GetMapping("room/add")
    public String addRoom(@RequestParam int roomNumber,
                              @RequestParam String roomType,
                              @RequestParam int baseCapacity,
                              @RequestParam int maxExtraBeds) {

        RoomType type = RoomType.displayName(roomType);
        Room newRoom = new Room(roomNumber, type, baseCapacity, maxExtraBeds);
        roomRepo.save(newRoom);

        log.info("Added new room with id {}", newRoom.getId());
        return "Room with room number " + roomNumber + " added";
    }

    /** Via Postman
     * {
     * 	"roomNumber": 17,
     *  "type": "SINGLEROOM",
     *  "baseCapacity": 1,
     *  "maxExtraBeds": 0
     *  }
     */
    @PostMapping("room/addPostman")
    public List<Room> addRoomByPostman(@RequestBody Room room) {
        roomRepo.save(room);
        log.info("Added new room with id {}", room.getId());
        return roomRepo.findAll();
    }

    /** Via Postman
     * {
     *     "id": 1,
     * 	   "roomNumber": 500,
     *     "type": "DOUBLEROOM",
     *     "baseCapacity": 1,
     *     "maxExtraBeds": 0
     * }
     */
    @PutMapping("room/updatePostman")
    public String updateRoom(@RequestBody Room room) {
        if (room.getId() == null) {
            log.warn("Room with id {} not found", room.getId());
            return "Room with id " + room.getId() + " not found";
        } else {
            Room roomToUpdate = roomRepo.findById(room.getId()).orElse(null);

            if (roomToUpdate == null) {
                roomRepo.save(room);
            } else {
                roomToUpdate.setRoomNumber(room.getRoomNumber());
                roomToUpdate.setType(room.getType());
                roomToUpdate.setBaseCapacity(room.getBaseCapacity());
                roomToUpdate.setMaxExtraBeds(room.getMaxExtraBeds());
                roomRepo.save(roomToUpdate);
            }
        }

        log.info("Updated room with id {}", room.getId());
        return "Room with id " + room.getId() + " updated";
    }

}
