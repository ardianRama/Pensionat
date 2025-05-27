package org.example.pensionat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int baseCapacity;

    private int maxExtraBeds;

    @OneToMany(mappedBy = "room")
    private List<Booking> myBookings;

    public Room(int roomNumber, RoomType type, int baseCapacity, int maxExtraBeds) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.baseCapacity = baseCapacity;
        this.maxExtraBeds = maxExtraBeds;
    }
}
