package org.example.pensionat.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.BookingService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.CustomerDto;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;
import org.example.pensionat.repository.BookingRepository;
import org.example.pensionat.repository.CustomerRepository;
import org.example.pensionat.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    //private final CustomerService customerService; //orsakar cirkulär referens
    //private final RoomService roomService;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    @Override
    public BookingDto entityBookingToBookingDto(Booking b) {
        return BookingDto.builder().checkIn(b.getCheckIn()).checkOut(b.getCheckOut()).extraBeds(b.getExtraBeds())
                .numberOfGuests(b.getNumberOfGuests()).build();
    }

    //@Override
    //public DetailedBookingDto entityBookingToDetailedBookingDto(Booking b) {
      //  return DetailedBookingDto.builder().id(b.getId()).checkIn(b.getCheckIn()).checkOut(b.getCheckOut())
        //        .extraBeds(b.getExtraBeds()).numberOfGuests(b.getNumberOfGuests())
          //      .customer(customerService.entityCustomerToCustomerDto(b.getCustomer()))
            //    .room(roomService.entityRoomToRoomDto(b.getRoom())).build();
    //}

    @Override
    public DetailedBookingDto entityBookingToDetailedBookingDto(Booking b) {
        return DetailedBookingDto.builder().id(b.getId()).checkIn(b.getCheckIn()).checkOut(b.getCheckOut())
                .extraBeds(b.getExtraBeds()).numberOfGuests(b.getNumberOfGuests())
                .customer(new CustomerDto(b.getCustomer().getId())).room(new RoomDto(b.getRoom().getId())).build();
    }

    @Override
    public Booking dtoDetailedBookingToEntityBooking(DetailedBookingDto b, Customer c, Room r) {
        return Booking.builder().id(b.getId()).checkIn(b.getCheckIn()).checkOut(b.getCheckOut())
                .extraBeds(b.getExtraBeds()).numberOfGuests(b.getNumberOfGuests())
                .customer(c).room(r).build();
    }

    @Override
    public List<DetailedBookingDto> getAllDetailedBooking() {
        return bookingRepository.findAll().stream().map(booking -> entityBookingToDetailedBookingDto(booking)).toList();
    }

    @Override
    public DetailedBookingDto getDetailedBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return entityBookingToDetailedBookingDto(booking);
    }

    @Override
    public String addBooking(DetailedBookingDto bookingDto) {
        Long roomId = bookingDto.getRoom().getId();
        LocalDate checkIn = bookingDto.getCheckIn();
        LocalDate checkOut = bookingDto.getCheckOut();

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(roomId, checkIn, checkOut);
        if (!overlappingBookings.isEmpty()) {
            return "The room is already booked for the selected dates.";
        }

        Customer customer = customerRepository.findById(bookingDto.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Room room = roomRepository.findById(bookingDto.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Booking booking = dtoDetailedBookingToEntityBooking(bookingDto, customer, room);

        bookingRepository.save(booking);
        return "Booking added successfully.";
    }

    @Override
    public String cancelBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            return "Booking with id " + bookingId + " does not exist";
        }

        bookingRepository.deleteById(bookingId);
        return "Booking cancelled successfully";
    }

    @Override
    public String updateBooking(Long id, DetailedBookingDto updatedBookingDto) {

        //hitta bokning som ska uppdateras
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        //så att kunden inte byts ut
        if (!existingBooking.getCustomer().getId().equals(updatedBookingDto.getCustomer().getId())) {
            throw new RuntimeException("Customer cannot be changed on an existing booking.");
        }

        //kolla att rummet är ledigt under det nya datumet
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                updatedBookingDto.getRoom().getId(),
                updatedBookingDto.getCheckIn(),
                updatedBookingDto.getCheckOut()
        ).stream().filter(b -> !b.getId().equals(id)).toList();

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Room is already booked during the selected dates.");
        }

        //hämtar nya eller samma rum
        Room room = roomRepository.findById(updatedBookingDto.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        existingBooking.setCheckIn(updatedBookingDto.getCheckIn());
        existingBooking.setCheckOut(updatedBookingDto.getCheckOut());
        existingBooking.setExtraBeds(updatedBookingDto.getExtraBeds());
        existingBooking.setNumberOfGuests(updatedBookingDto.getNumberOfGuests());
        existingBooking.setRoom(room);

        bookingRepository.save(existingBooking);
        return "Booking updated successfully";
    }

    //@Override
    //public Booking dtoBookingToEntityBooking(BookingDto b) {
      //  return Booking.builder().checkIn(b.getCheckIn()).checkOut(b.getCheckOut()).extraBeds(b.getExtraBeds())
        //        .numberOfGuests(b.getNumberOfGuests()).build();
    //}
}
