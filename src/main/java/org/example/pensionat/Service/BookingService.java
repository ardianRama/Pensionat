package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.models.Booking;

import java.util.List;

public interface BookingService {

    public BookingDto entityBookingToBookingDto (Booking booking);

    public DetailedBookingDto entityBookingToDetailedBookingDto (Booking booking);

    public List<DetailedBookingDto> getAllDetailedBooking();

    //kanske inte behövs
    //public Booking dtoBookingToEntityBooking(BookingDto booking);

}
