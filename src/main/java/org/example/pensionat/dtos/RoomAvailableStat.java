package org.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pensionat.models.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomAvailableStat {

    private int roomNumber;

    private RoomType roomType;

    private int baseCapacity;

    private int maxExtraBeds;
}
