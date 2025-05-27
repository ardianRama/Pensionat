package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.BookingService;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.service.CustomerService;
import org.example.pensionat.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    //funkar
    @GetMapping("/list")
    public String getAllBookings(Model model) {
        log.info("Get all bookings");
        List<DetailedBookingDto> bookings = bookingService.getAllDetailedBooking();
        model.addAttribute("bookings", bookings);
        return "booking/bookingList";
    }

    //funkar
    @GetMapping("/{id}")
    public String getBookingById(@PathVariable Long id, Model model) {
        log.info("Get booking with id {}", id);
        DetailedBookingDto booking = bookingService.getDetailedBookingById(id);
        model.addAttribute("booking", booking);
        return "booking/bookingView";
    }

    //funkar
    @GetMapping("/add")
    public String showAddBookingForm(Model model) {
        model.addAttribute("booking", new DetailedBookingDto());
        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        model.addAttribute("rooms", roomService.getAllDetailedRooms());
        return "booking/bookingAddForm";
    }

    //funkar (men inte 100% validering för rummen pga ej kustomiserad validering)
    @PostMapping("/add")
    public String addBookingSubmit(@Valid @ModelAttribute("booking") DetailedBookingDto bookingDto,
                                   BindingResult bindingResult,
                                   Model model) {
        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        model.addAttribute("rooms", roomService.getAllDetailedRooms());

        if (bindingResult.hasErrors()) {
            return "booking/bookingAddForm";
        }

        String message = bookingService.addBooking(bookingDto);

        if ("Booking added successfully.".equals(message)) {
            model.addAttribute("serviceMessage", message);
            model.addAttribute("booking", new DetailedBookingDto());
        } else {
            model.addAttribute("serviceError", message);
            model.addAttribute("booking", bookingDto);
        }

        return "booking/bookingAddForm";
    }

    //funkar
    @PostMapping("/{id}/delete")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = bookingService.cancelBooking(id);
        redirectAttributes.addFlashAttribute("deleteMessage", message);
        return "redirect:/booking/list";
    }


    //@RestController!, behöver göras om.
    @PutMapping("/booking/{id}/update")
    public ResponseEntity<String> updateBooking(@PathVariable Long id,
                                                @RequestBody @Valid DetailedBookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

}
