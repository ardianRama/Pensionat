package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.models.Booking;

public interface BookingService {

    public BookingDto entityBookingToBookingDto (Booking booking);

    //kanske inte behövs
    public Booking dtoBookingToEntityBooking(BookingDto booking);
}
