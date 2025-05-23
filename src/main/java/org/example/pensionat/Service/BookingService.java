package org.example.pensionat.Service;

import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;

import java.util.List;

public interface BookingService {

    public BookingDto entityBookingToBookingDto (Booking booking);

    public DetailedBookingDto entityBookingToDetailedBookingDto (Booking booking);

    public Booking dtoDetailedBookingToEntityBooking (DetailedBookingDto b, Customer c, Room r);

    public List<DetailedBookingDto> getAllDetailedBooking();

    public DetailedBookingDto getDetailedBookingById(Long id);

    public String addBooking(DetailedBookingDto booking);

    public String cancelBooking(Long bookingId);


    //kanske inte behövs
    //public Booking dtoBookingToEntityBooking(BookingDto booking);

}
