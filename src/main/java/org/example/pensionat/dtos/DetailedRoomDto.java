package org.example.pensionat.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pensionat.models.RoomType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRoomDto {

    private Long id;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be at least 1")
    private int roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Base capacity is required")
    @Min(value = 1, message = "Base capacity must be at least 1")
    @Max(value = 2, message = "Base capacity can be at most 2")
    private int baseCapacity;

    @NotNull(message = "Must be 0, 1 or 2 depending on the room type")
    @Min(value = 0, message = "Max extra beds must be 0 or more")
    @Max(value = 2, message = "Max extra beds can be at most 2")
    private int maxExtraBeds;

    private List<BookingDto> myBookings;
}
