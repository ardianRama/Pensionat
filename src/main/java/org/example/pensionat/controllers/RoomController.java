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
import org.springframework.http.ResponseEntity;
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
        return "roomList";
    }

    //funkar
    @GetMapping("/view/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        log.info("Get room by id: {}", id);
        Optional<DetailedRoomDto> roomOpt = roomService.getDetailedRoomById(id);

        if (roomOpt.isPresent()) {
            model.addAttribute("room", roomOpt.get());
            return "roomDetails";
        } else {
            return "redirect:/room/list";
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
        return "roomAddForm";
    }

    //funkar
    @PostMapping("/add")
    public String addRoomSubmit(@Valid @ModelAttribute("room") DetailedRoomDto room,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roomTypes", RoomType.values());
            return "roomAddForm";
        }

        String validationError = roomService.addRoom(room);

        if (validationError != null) {
            model.addAttribute("roomTypes", RoomType.values());
            model.addAttribute("serviceError", validationError);
            return "roomAddForm";  // visa formuläret igen med felmeddelande
        }

        return "redirect:/room/list";
    }

    //funkar inte riktigt
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<DetailedRoomDto> roomOpt = roomService.getDetailedRoomById(id);
        if (roomOpt.isEmpty()) {
            // hantera rum finns ej, t ex redirect med felmeddelande
            return "redirect:/room/list";
        }
        model.addAttribute("room", roomOpt.get());
        model.addAttribute("roomTypes", RoomType.values());
        return "roomUpdateForm";
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
            return "roomUpdateForm";
        }

        String message = roomService.updateRoom(id, roomDto);

        // Kontrollera om det är ett felmeddelande
        if (message.contains("cannot be updated") || message.contains("not found") || message.contains("must")) {
            model.addAttribute("roomTypes", RoomType.values());
            model.addAttribute("errorMessage", message);
            return "roomUpdateForm";
        }

        redirectAttributes.addFlashAttribute("updateMessage", message); // visa lyckat meddelande i roomList
        return "redirect:/room/list";
    }

    //RestController
    //@PutMapping("room/{id}/update")
    //public String updateRoom(@PathVariable Long id, @RequestBody DetailedRoomDto roomDto) { //ingen @Valid behövs
      //  log.info("Update room with id: {}", id);
        //return roomService.updateRoom(id, roomDto);
    //}

    //http://localhost:8080/room/search?checkIn=2025-05-20&checkOut=2025-05-28&guests=4
    @GetMapping("room/search")
    public List<RoomAvailableStat> searchAvailableRooms(@RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                                        @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                                                        @RequestParam("guests") int numberOfGuests) {
        return roomService.searchAvailableRooms(checkIn, checkOut, numberOfGuests);
    }

}
