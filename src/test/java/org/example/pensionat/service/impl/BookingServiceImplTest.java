package org.example.pensionat.service.impl;

import org.example.pensionat.dtos.CustomerDto;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.BookingRepository;
import org.example.pensionat.repository.CustomerRepository;
import org.example.pensionat.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//...
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Long id = 1L;

    private LocalDate checkIn = LocalDate.of(2025, 06, 05);

    private LocalDate checkOut = LocalDate.of(2025, 06, 10);

    private int extraBeds = 2;

    private int numberOfGuests = 2;

    private Customer customer = new Customer(25L,"Kalle", "kalle@gmail.com", "0701112233",
            "långtbortistan 123", new ArrayList<>());

    private Room room = new Room(10L, 205, RoomType.DOUBLEROOM, 2, 2, new ArrayList<>());

    private Booking booking = new Booking(id, checkIn, checkOut, extraBeds, numberOfGuests, customer, room);

    private DetailedBookingDto dtoBooking = new DetailedBookingDto(id, checkIn, checkOut, extraBeds, numberOfGuests,
            new CustomerDto(customer.getId()), new RoomDto(room.getId()));

    @Test
    void entityBookingToDetailedBookingDto() {
        DetailedBookingDto actual = bookingService.entityBookingToDetailedBookingDto(booking);

        assertNotNull(actual.getCustomer(), "Customer should not be null");
        assertNotNull(actual.getRoom(), "Room should not be null");

        assertEquals(booking.getCheckIn(), actual.getCheckIn(), "Should be the same date");
        assertEquals(booking.getCheckOut(), actual.getCheckOut(), "Should be the same date");
        assertEquals(booking.getExtraBeds(), actual.getExtraBeds(), "Should be the same number");
        assertEquals(booking.getNumberOfGuests(), actual.getNumberOfGuests(), "Should be the same number");
        assertEquals(booking.getId(), actual.getId(), "Should be the same booking id");
        assertEquals(booking.getCustomer().getId(), actual.getCustomer().getId(), "Should be the same customer id");
        assertEquals(booking.getRoom().getId(), actual.getRoom().getId(), "Should be the same room id");
    }

    @Test
    void dtoDetailedBookingToEntityBooking() {
        Booking actual = bookingService.dtoDetailedBookingToEntityBooking(dtoBooking, customer, room);

        assertNotNull(actual.getCustomer(), "Customer should not be null");
        assertNotNull(actual.getRoom(), "Room should not be null");

        assertEquals(dtoBooking.getCheckIn(), actual.getCheckIn(), "Should be the same date");
        assertEquals(dtoBooking.getCheckOut(), actual.getCheckOut(), "Should be the same date");
        assertEquals(dtoBooking.getExtraBeds(), actual.getExtraBeds(), "Should be the same number");
        assertEquals(dtoBooking.getNumberOfGuests(), actual.getNumberOfGuests(), "Should be the same number");
        assertEquals(dtoBooking.getId(), actual.getId(), "Should be the same booking id");
        assertEquals(dtoBooking.getCustomer().getId(), actual.getCustomer().getId(), "Should be the same customer id");
        assertEquals(dtoBooking.getRoom().getId(), actual.getRoom().getId(), "Should be the same room id");
    }

    @Test
    void getAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service2 = new BookingServiceImpl(bookingRepository, customerRepository, roomRepository);
        List<DetailedBookingDto> allBookings = service2.getAllDetailedBooking();

        assertTrue(allBookings.size() == 1);
    }

}