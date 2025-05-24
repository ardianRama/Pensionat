package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.BookingService;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    @GetMapping("booking")
    public List<DetailedBookingDto> getAllBookings() {
        log.info("Get all bookings");
        return bookingService.getAllDetailedBooking();
    }

    @GetMapping("booking/{id}")
    public DetailedBookingDto getBookingById(@PathVariable Long id) {
        log.info("Get booking with id {}", id);
        return bookingService.getDetailedBookingById(id);
    }

    @PostMapping("booking/add")
    public String addBooking(@RequestBody @Valid DetailedBookingDto booking) {
        log.info("Added booking {}", booking);
        return bookingService.addBooking(booking);
    }

    @DeleteMapping("booking/{id}/delete")
    public String deleteBooking(@PathVariable Long id) {
        log.info("Cancel booking with ID {}", id);
        return bookingService.cancelBooking(id);
    }

    @PutMapping("/booking/{id}/update")
    public ResponseEntity<String> updateBooking(@PathVariable Long id,
                                                @RequestBody DetailedBookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

}
