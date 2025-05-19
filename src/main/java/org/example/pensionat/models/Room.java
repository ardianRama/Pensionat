package org.example.pensionat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private int roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int baseCapacity; //1 eller 2

    private int maxExtraBeds; //0, 1 eller 2

    public Room(int roomNumber, RoomType type, int baseCapacity, int maxExtraBeds) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.baseCapacity = baseCapacity;
        this.maxExtraBeds = maxExtraBeds;
    }
}
