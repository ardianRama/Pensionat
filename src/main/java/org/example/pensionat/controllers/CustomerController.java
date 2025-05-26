package org.example.pensionat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.CustomerService;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

// sebbe - Tar bort @RestController och använder istället @Controller
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final CustomerService customerService;

    @ResponseBody
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
    public String addCustomer (@RequestBody @Valid DetailedCustomerDto customer) {
        log.info("Added new customer with id: {}", customer.getId());
        return customerService.addCustomer(customer);
    }

    @PutMapping("customer/{id}/update") //behövs ingen @Valid
    public String updateCustomer(@PathVariable Long id, @RequestBody DetailedCustomerDto customerDto) {
        log.info("Update customer with id: {}", id);
        return customerService.updateCustomer(id, customerDto);
    }

    // http://localhost:8080/register
    // Visar registrering form i browser.
    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("customer", new DetailedCustomerDto());
        return "/register";
    }

    // redirect to findCustomer.
    @PostMapping("/register")
    public String handlerRegister(@ModelAttribute DetailedCustomerDto detailedCustomerDto){
        customerService.addCustomer(detailedCustomerDto);
        return "redirect:/search-customer";
    }

    // Här sökes kunder med namn och email som input.
    @GetMapping("/search-customer")
    public String showSearchForm() {
        return "customer-view";
    }

    //fungerar! visar info om inmatade kunden.
    // Varför har /customer-management och /search-customer samma view.
    @GetMapping("/customer-management")
    public String findCustomer(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String email,
                               Model model) {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            model.addAttribute("error", "Namn och e-post får inte vara tomma.");
            return "customer-view";
        }
        Optional<DetailedCustomerDto> customer = customerService.findByNameAndEmail(name, email);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
        }
        else {
            model.addAttribute("errorFindCustomer", "Kunden med angivet namn och e-post hittades inte.");
        }
        return "customer-view";
    }

    // fungerar bra.
    @PostMapping("/delete-customer")
    public String deleteCustomer(@RequestParam String name, @RequestParam String email, Model model) {
        boolean deleted = customerService.deleteCustomerByNameAndEmail(name,email);
        if (deleted) {
            model.addAttribute("message1", "Kund raderad korrekt.");
        } else {
            model.addAttribute("error1", "Kunden raderas inte på grund av bokning eller annat fel.");
        }
        return "customer-view";
    }

    // uppdatera
    // <a th:href="@{/customer-update-form(name=${customer.name}, email=${customer.email})}" class="btn btn-primary">Uppdatera kund</a>
    // <a th:href="@{/customer-update-form(name=${customer.name}, email=${customer.email})}" class="btn btn-primary">Uppdatera kund</a>
    @GetMapping("/customer-update-form")
    public String showUpdateForm(@RequestParam String name,
                                 @RequestParam String email,
                                 Model model) {
        Optional<DetailedCustomerDto> customerOpt = customerService.findByNameAndEmail(name, email);

        if (customerOpt.isPresent()) {
            model.addAttribute("customer", customerOpt.get());
            return "customer-update-form";
        } else {
            model.addAttribute("errorShowUpdateForm", "Kund hittades inte.");
            return "kundinfo";
        }
    }

    // kontrollera om den fungerar.
    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute DetailedCustomerDto customerDto, RedirectAttributes redirectAttributes) {
        boolean updated = customerService.updateCustomer(customerDto.getEmail(), customerDto);

        if (updated) {
            redirectAttributes.addFlashAttribute("messageUpdateCustomer", "Kunduppgifter uppdaterade.");
        } else {
            redirectAttributes.addFlashAttribute("errorUpdateCustomer", "Kund kunde inte uppdateras.");
        }

        redirectAttributes.addAttribute("name", customerDto.getName());
        redirectAttributes.addAttribute("email", customerDto.getEmail());

        return "redirect:/customer-management";
    }






}