package org.example.pensionat.controllers;

import org.example.pensionat.Service.CustomerService;
import org.example.pensionat.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    /*
    private final CustomerRepository customerRepository;
    CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
     */

    @Autowired
    private final CustomerService customerService;
    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String Register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }


    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String address,
                                 Model model) {
        if (customerService.existsUsername(username)) {
            model.addAttribute("eror", "Username is already in use");
            return "register";
        } else {
            customerService.registerUsername(username, password, name, email, phoneNumber, address);
            return "redirect:/login";
        }
    }
}
