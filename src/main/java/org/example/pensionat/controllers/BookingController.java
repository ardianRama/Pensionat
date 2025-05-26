package org.example.pensionat.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.RoomService;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping("bookings")
    public List<DetailedBookingDto> getAllBookings() {
        log.info("Get all bookings");
        return bookingService.getAllDetailedBooking();
    }

    /*
    //sebbe visar booking html
    @GetMapping("/booking")
    public String showBooking(Model model){
        model.addAttribute("rooms",roomService.getAllDetailedRooms());
        return "booking";
    }
    */


}
