package org.example.pensionat.service.impl;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomAvailableStat;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.RoomRepository;
import org.example.pensionat.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RoomServiceImplTest {
    @Mock
    BookingService bookingService;
    @Mock
    RoomRepository roomRepository;
    @InjectMocks
    RoomServiceImpl roomServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEntityRoomToDetailedRoomDto() {
        when(bookingService.entityBookingToBookingDto(any(Booking.class))).thenReturn(new BookingDto(LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0));

        DetailedRoomDto result = roomServiceImpl.entityRoomToDetailedRoomDto(new Room(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new Booking(Long.valueOf(1), LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0, new Customer(Long.valueOf(1), "name", "email", "phoneNumber", "address", List.of()), null))));
        Assertions.assertEquals(new DetailedRoomDto(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new BookingDto(LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0))), result);
    }

    @Test
    void testEntityRoomToRoomDto() {
        RoomDto result = roomServiceImpl.entityRoomToRoomDto(new Room(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new Booking(Long.valueOf(1), LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0, new Customer(Long.valueOf(1), "name", "email", "phoneNumber", "address", List.of()), null))));
        Assertions.assertEquals(new RoomDto(Long.valueOf(1)), result);
    }

    @Test
    void testEntityRoomToRoomAvailableStatDto() {
        RoomAvailableStat result = roomServiceImpl.entityRoomToRoomAvailableStatDto(new Room(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new Booking(Long.valueOf(1), LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0, new Customer(Long.valueOf(1), "name", "email", "phoneNumber", "address", List.of()), null))));
        Assertions.assertEquals(new RoomAvailableStat(0, RoomType.SINGLEROOM, 0, 0), result);
    }

    @Test
    void testDtoDetailedRoomToEntityRoom() {
        Room result = roomServiceImpl.dtoDetailedRoomToEntityRoom(new DetailedRoomDto(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new BookingDto(LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0))));
        Assertions.assertEquals(new Room(Long.valueOf(1), 0, RoomType.SINGLEROOM, 0, 0, List.of(new Booking(Long.valueOf(1), LocalDate.of(2025, Month.MAY, 24), LocalDate.of(2025, Month.MAY, 24), 0, 0, new Customer(Long.valueOf(1), "name", "email", "phoneNumber", "address", List.of()), null))), result);
    }


}
