package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.models.Booking;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Override
    public BookingDto entityBookingToBookingDto(Booking b) {
        return BookingDto.builder().checkIn(b.getCheckIn()).checkOut(b.getCheckOut()).extraBeds(b.getExtraBeds())
                .numberOfGuests(b.getNumberOfGuests()).build();
    }

    //kanske inte behövs
    @Override
    public Booking dtoBookingToEntityBooking(BookingDto b) {
        return Booking.builder().checkIn(b.getCheckIn()).checkOut(b.getCheckOut()).extraBeds(b.getExtraBeds())
                .numberOfGuests(b.getNumberOfGuests()).build();
    }
}
