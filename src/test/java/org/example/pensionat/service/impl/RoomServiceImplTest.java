package org.example.pensionat.service.impl;

import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.RoomRepository;
import org.example.pensionat.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl = new RoomServiceImpl(bookingService, roomRepository); //kanske måste manuellt injekta

    private long id = 1L;

    private int roomNumber = 105;

    private RoomType roomType = RoomType.SINGLEROOM;

    private int baseCapacity = 1;

    private int maxExtraBeds = 0;

    Room room = new Room(id, roomNumber, roomType, baseCapacity, maxExtraBeds, new ArrayList<>());

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(id).roomNumber(roomNumber)
            .roomType(roomType).baseCapacity(baseCapacity).maxExtraBeds(maxExtraBeds).build();

    @Test
    void entityRoomToDetailedRoomDto() {
       DetailedRoomDto actual = roomServiceImpl.entityRoomToDetailedRoomDto(room);

        assertEquals(actual.getId(), detailedRoomDto.getId(), "Id should be the same");
        assertEquals(actual.getRoomNumber(), detailedRoomDto.getRoomNumber(), "Room number should be the same");
        assertEquals(actual.getRoomType(), detailedRoomDto.getRoomType(), "Room type should be the same");
        assertEquals(actual.getBaseCapacity(), detailedRoomDto.getBaseCapacity(), "Base capacity should be the same");
        assertEquals(actual.getMaxExtraBeds(), detailedRoomDto.getMaxExtraBeds(), "Max extra beds should be the same");
    }

    @Test
    void detailedRoomToEntityRoom() {
        Room actual = roomServiceImpl.dtoDetailedRoomToEntityRoom(detailedRoomDto);

        assertEquals(actual.getId(), detailedRoomDto.getId(), "Id should be the same");
        assertEquals(actual.getRoomNumber(), detailedRoomDto.getRoomNumber(), "Room number should be the same");
        assertEquals(actual.getType(), detailedRoomDto.getRoomType(), "Room type should be the same");
        assertEquals(actual.getBaseCapacity(), detailedRoomDto.getBaseCapacity(), "Base capacity should be the same");
        assertEquals(actual.getMaxExtraBeds(), detailedRoomDto.getMaxExtraBeds(), "Max extra beds should be the same");
    }

    @Test
    void getAllRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));
        RoomServiceImpl service2 = new RoomServiceImpl(bookingService, roomRepository);
        List<DetailedRoomDto> allRooms = service2.getAllDetailedRooms();

        assertTrue(allRooms.size() == 1);
    }
}
