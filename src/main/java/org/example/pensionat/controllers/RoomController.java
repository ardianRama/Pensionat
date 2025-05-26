package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.service.RoomService;
import org.example.pensionat.dtos.DetailedRoomDto;
import org.example.pensionat.dtos.RoomAvailableStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;

    //funkar
    @GetMapping("/list")
    public String getAllRooms(Model model) {
        log.info("Get all rooms");
        List<DetailedRoomDto> rooms = roomService.getAllDetailedRooms();
        model.addAttribute("rooms", rooms);
        return "room/roomList";
    }

    //funkar
    @GetMapping("/view/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        log.info("Get room by id: {}", id);
        Optional<DetailedRoomDto> roomOpt = roomService.getDetailedRoomById(id);

        if (roomOpt.isPresent()) {
            model.addAttribute("room", roomOpt.get());
            return "room/roomDetails";
        } else {
            return "redirect:room/room/list";
        }
    }

    //funkar
    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = roomService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("deleteMessage", message);
        return "redirect:/room/list";
    }

    //funkar
    @GetMapping("/add")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new DetailedRoomDto());
        model.addAttribute("roomTypes", RoomType.values());  // Om du vill visa en dropdown med rumstyper
        return "room/roomAddForm";
    }

    //funkar
    @PostMapping("/add")
    public String addRoomSubmit(@Valid @ModelAttribute("room") DetailedRoomDto room,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roomTypes", RoomType.values());
            return "room/roomAddForm";
        }

        String validationError = roomService.addRoom(room);

        if (validationError != null) {
            model.addAttribute("roomTypes", RoomType.values());
            model.addAttribute("serviceError", validationError);
            return "room/roomAddForm";
        }

        return "redirect:/room/list";
    }

    //funkar inte riktigt
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<DetailedRoomDto> roomOpt = roomService.getDetailedRoomById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/room/list";
        }
        model.addAttribute("room", roomOpt.get());
        model.addAttribute("roomTypes", RoomType.values());
        return "room/roomUpdateForm";
    }

    //funkar inte riktigt
    @PostMapping("/update/{id}")
    public String updateRoomSubmit(@PathVariable Long id,
                                   @Valid @ModelAttribute("room") DetailedRoomDto roomDto,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roomTypes", RoomType.values());
            return "room/roomUpdateForm";
        }

        String message = roomService.updateRoom(id, roomDto);

        // Kontrollera om det är ett felmeddelande
        if (message.contains("cannot be updated") || message.contains("not found") || message.contains("must")) {
            model.addAttribute("roomTypes", RoomType.values());
            model.addAttribute("errorMessage", message);
            return "room/roomUpdateForm";
        }

        redirectAttributes.addFlashAttribute("updateMessage", message); // visa lyckat meddelande i roomList
        return "redirect:/room/list";
    }

    //RestController för update, ta inte bort än förrän controller varianten är klar
    //@PutMapping("room/{id}/update")
    //public String updateRoom(@PathVariable Long id, @RequestBody DetailedRoomDto roomDto) { //ingen @Valid behövs
      //  log.info("Update room with id: {}", id);
        //return roomService.updateRoom(id, roomDto);
    //}

    //funkar
    @GetMapping("/search")
    public String showSearchForm() {
        return "room/roomSearch";
    }

    //funkar
    @GetMapping(value = "/search", params = {"checkIn", "checkOut", "guests"})
    public String searchAvailableRooms(@RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                       @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                                       @RequestParam("guests") int numberOfGuests,
                                       Model model) {

        List<RoomAvailableStat> results = roomService.searchAvailableRooms(checkIn, checkOut, numberOfGuests);

        if (results.isEmpty()) {
            model.addAttribute("noResults", "No available rooms found for the selected dates and number of guests.");
        } else {
            model.addAttribute("availableRooms", results);
        }

        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("guests", numberOfGuests);

        return "room/roomSearch";
    }

}
