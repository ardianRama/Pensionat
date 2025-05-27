package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.service.CustomerService;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final CustomerService customerService;

    @GetMapping("/list")
    public String getAllCustomers(Model model) {
        log.info("Get all customers");
        model.addAttribute("customers", customerService.getAllDetailedCustomer());
        return "customer/customerList";
    }

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

    //*
    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = customerService.deleteCustomer(id);
        redirectAttributes.addAttribute("message", message);
        return "redirect:/customer/list";
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("detailedCustomerDto", new DetailedCustomerDto());
        return "customer/addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("detailedCustomerDto") DetailedCustomerDto detailedCustomerDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customer/addCustomer";
        }
        String message = customerService.addCustomer(detailedCustomerDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/customer/list";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<DetailedCustomerDto> optionalCustomer = customerService.getDetailedCustomerById(id);
        if (optionalCustomer.isPresent()) {
            model.addAttribute("customer", optionalCustomer.get());
            return "customer/updateCustomerForm";
        } else {
            redirectAttributes.addFlashAttribute("message", "Customer not found.");
            return "redirect:/customer/list";
        }
    }

    @PostMapping("/{id}/update")
    public String updateCustomer(@PathVariable Long id,
                                 @ModelAttribute("customer") @Valid DetailedCustomerDto customerDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customer/updateCustomerForm";
        }

        String result = customerService.updateCustomer(id, customerDto);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/customer/list";
    }
}
