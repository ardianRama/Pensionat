package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomDto;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.service.BookingService;
import org.example.pensionat.dtos.DetailedBookingDto;
import org.example.pensionat.service.CustomerService;
import org.example.pensionat.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService    bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    @GetMapping("/list")
    public String getAllBookings(Model model) {
        log.info("Get all bookings");
        List<DetailedBookingDto> bookings = bookingService.getAllDetailedBooking();
        model.addAttribute("bookings", bookings);
        return "booking/bookingList";
    }

    @GetMapping("/{id}")
    public String getBookingById(@PathVariable Long id, Model model) {
        log.info("Get booking with id {}", id);
        DetailedBookingDto booking = bookingService.getDetailedBookingById(id);
        model.addAttribute("booking", booking);
        return "booking/bookingView";
    }

    @GetMapping("/add")
    public String showAddBookingForm(Model model) {
        model.addAttribute("booking", new DetailedBookingDto());
        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        model.addAttribute("rooms", roomService.getAllDetailedRooms());
        return "booking/bookingAddForm";
    }

    //*
    @PostMapping("/add")
    public String addBookingSubmit(@Valid @ModelAttribute("booking") DetailedBookingDto bookingDto,
                                   BindingResult bindingResult,
                                   Model model) {

        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        model.addAttribute("rooms", roomService.getAllDetailedRooms());

        if (bookingDto.getRoom() != null && bookingDto.getRoom().getId() != null) {
            Optional<DetailedRoomDto> optionalRoom = roomService.getDetailedRoomById(bookingDto.getRoom().getId());

            if (optionalRoom.isPresent()) {
                DetailedRoomDto selected = optionalRoom.get();
                RoomType roomType = selected.getRoomType();

                if (roomType == RoomType.SINGLEROOM && bookingDto.getExtraBeds() > 0) {
                    bindingResult.rejectValue("extraBeds", "extraBeds.invalid", "Extrasängar är inte tillåtna i singelrum.");
                }

                if (roomType == RoomType.DOUBLEROOM && (bookingDto.getExtraBeds() < 0 || bookingDto.getExtraBeds() > 2)) {
                    bindingResult.rejectValue("extraBeds", "extraBeds.invalid", "Antal extrasängar i dubbelrum måste vara mellan 0 och 2.");
                }
            } else {
                bindingResult.rejectValue("room.id", "room.invalid", "Valt rum existerar inte.");
            }
        } else {
            bindingResult.rejectValue("room", "room.invalid", "Du måste välja ett rum.");
        }

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


    @PostMapping("/{id}/delete")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = bookingService.cancelBooking(id);
        redirectAttributes.addFlashAttribute("deleteMessage", message);
        return "redirect:/booking/list";
    }

    @GetMapping("/{id}/edit")
    public String showEditBookingForm(@PathVariable Long id, Model model) {
        try {
            DetailedBookingDto booking = bookingService.getDetailedBookingById(id);
            model.addAttribute("booking", booking);
            model.addAttribute("rooms", roomService.getAllDetailedRooms());
            return "booking/editBookingForm";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //*
    @PostMapping("/{id}/update")
    public String updateBooking(@PathVariable Long id,
                                @ModelAttribute("booking") @Valid DetailedBookingDto bookingDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("rooms", roomService.getAllDetailedRooms());
            return "booking/editBookingForm";
        }

        try {
            String message = bookingService.updateBooking(id, bookingDto);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/booking/list";
        } catch (RuntimeException e) {
            model.addAttribute("rooms", roomService.getAllDetailedRooms());
            model.addAttribute("errorMessage", e.getMessage());
            return "booking/editBookingForm";
        }
    }
}
