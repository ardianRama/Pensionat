package org.example.pensionat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    //TODO validering

    @Id
    @GeneratedValue
    private Long id;

    private int roomNumber; //TODO bör vara unique

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int baseCapacity; //1 eller 2

    private int maxExtraBeds; //0, 1 eller 2

    //TODO ändra mappedBy till roomId
    //se hur många bookings ett rum har
    @OneToMany(mappedBy = "room")
    private List<Booking> myBookings;

    public Room(int roomNumber, RoomType type, int baseCapacity, int maxExtraBeds) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.baseCapacity = baseCapacity;
        this.maxExtraBeds = maxExtraBeds;
    }
}
