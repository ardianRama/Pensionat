package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.CustomerService;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final CustomerService customerService;

    //funkar
    @GetMapping("/list")
    public String getAllCustomers(Model model) {
        log.info("Get all customers");
        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        return "customer/customerList";
    }

    //funkar
    @GetMapping("/{id}")
    public String showCustomerDetails(@PathVariable Long id, Model model) {
        Optional<DetailedCustomerDto> optionalCustomer = customerService.getDetailedCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            model.addAttribute("serviceError", "Customer not found.");
            return "redirect:/customer/list";
        }

        model.addAttribute("customer", optionalCustomer.get());
        return "customer/customerDetails";
    }

    //funkar men får inte fram meddelande
    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = customerService.deleteCustomer(id);
        redirectAttributes.addAttribute("message", message);
        return "redirect:/customer/list";
    }


    //RestController
    @PostMapping("customer/add")
    public String addCustomer (@RequestBody @Valid DetailedCustomerDto customer) {
        log.info("Added new customer with id: {}", customer.getId());
        return customerService.addCustomer(customer);
    }

    //RestController
    @PutMapping("customer/{id}/update")
    public String updateCustomer(@PathVariable Long id, @RequestBody DetailedCustomerDto customerDto) {
        log.info("Update customer with id: {}", id);
        return customerService.updateCustomer(id, customerDto);
    }
}
