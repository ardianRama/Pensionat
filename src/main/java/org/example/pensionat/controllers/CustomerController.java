package org.example.pensionat.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.CustomerService;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final CustomerService customerService;

    @GetMapping("customer")
    public List<DetailedCustomerDto> getAllCustomers() {
        log.info("Get all customers");
        return customerService.getAllDetailedCustomer();
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<DetailedCustomerDto> getCustomerById(@PathVariable Long id) {
        Optional<DetailedCustomerDto> dto = customerService.getDetailedCustomerById(id);
        log.info("Get customer by id: {}", id);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); //mappa om till ResponseEntity
    }

    @DeleteMapping("customer/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        log.info("Delete customer by id: {}", id);
        return customerService.deleteCustomer(id);
    }

    @PostMapping("customer/add")
    public String addRoom (@RequestBody DetailedCustomerDto customer) {
        log.info("Added new room with id: {}", customer.getId());
        return customerService.addCustomer(customer);
    }

    @PutMapping("customer/{id}/update")
    public String updateCustomer(@PathVariable Long id, @RequestBody DetailedCustomerDto customerDto) {
        log.info("Update customer with id: {}", id);
        return customerService.updateCustomer(id, customerDto);
    }


    /**

    /*
    private final CustomerRepository customerRepository;
    CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
     */

    /**

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
     */
}
