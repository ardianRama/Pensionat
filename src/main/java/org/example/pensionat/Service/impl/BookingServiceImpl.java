package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.CustomerService;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.CustomerDto;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    //private final CustomerService customerService; //orsakar cirkulär referens
    //private final RoomService roomService;
    private final BookingRepository bookingRepository;

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
                .customer(new CustomerDto(b.getId())).room(new RoomDto(b.getId())).build();
    }

    @Override
    public List<DetailedBookingDto> getAllDetailedBooking() {
        return bookingRepository.findAll().stream().map(booking -> entityBookingToDetailedBookingDto(booking)).toList();
    }

    //kanske inte behövs
    //@Override
    //public Booking dtoBookingToEntityBooking(BookingDto b) {
      //  return Booking.builder().checkIn(b.getCheckIn()).checkOut(b.getCheckOut()).extraBeds(b.getExtraBeds())
        //        .numberOfGuests(b.getNumberOfGuests()).build();
    //}
}
