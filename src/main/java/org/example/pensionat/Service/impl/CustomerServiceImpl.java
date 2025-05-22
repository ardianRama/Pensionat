package org.example.pensionat.Service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pensionat.Service.BookingService;
import org.example.pensionat.Service.CustomerService;
import org.example.pensionat.dtos.BookingDto;
import org.example.pensionat.dtos.CustomerDto;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;
import org.example.pensionat.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingService bookingService;

    @Override
    public DetailedCustomerDto entityCustomerToDetailedCustomerDto(Customer c) {
        return DetailedCustomerDto.builder().id(c.getId()).name(c.getName()).email(c.getEmail())
               .phoneNumber(c.getPhoneNumber()).address(c.getAddress()).myBookings(c.getMyBookings()
                       .stream().map(customers -> bookingService.entityBookingToBookingDto(customers))
                        .toList()).build();
    }

    @Override
    public CustomerDto entityCustomerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).build();
    }

    @Override
    public Customer dtoDetailedCustomerToEntityCustomer(DetailedCustomerDto c) {
        return Customer.builder().id(c.getId()).name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).address(c.getAddress()).build(); //ingen myBookings
    }

    @Override
    public List<DetailedCustomerDto> getAllDetailedCustomer() {
        return customerRepository.findAll().stream()
                .map(customer -> entityCustomerToDetailedCustomerDto(customer)).toList();
    }

    @Override
    public Optional<DetailedCustomerDto> getDetailedCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> entityCustomerToDetailedCustomerDto(customer));
    }

    @Override
    public String deleteCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if(customer.getMyBookings() != null && !customer.getMyBookings().isEmpty()) {
                return "Cannot delete customer with id " + id + " because it has existing bookings.";
            }
            customerRepository.delete(customer);
            return "Customer with id " + id + " has been deleted.";
        } else {
            return "Customer with id " + id + " does not exist.";
        }
    }

    @Override
    public String addCustomer(DetailedCustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            return "Customer with email " + customerDto.getEmail() + " already exists.";
        }
        Customer customer = dtoDetailedCustomerToEntityCustomer(customerDto);
        customerRepository.save(customer);
        return "Customer with id " + customer.getId() + " has been created.";
    }


    @Override
    public String updateCustomer(Long id, DetailedCustomerDto updatedCustomerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            return "Customer with id " + id + " not found.";
        }

        Customer existingCustomer = optionalCustomer.get();

        if (updatedCustomerDto.getName() != null) {
            existingCustomer.setName(updatedCustomerDto.getName());
        }
        if (updatedCustomerDto.getEmail() != null) {
            existingCustomer.setEmail(updatedCustomerDto.getEmail());
        }
        if (updatedCustomerDto.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(updatedCustomerDto.getPhoneNumber());
        }
        if (updatedCustomerDto.getAddress() != null) {
            existingCustomer.setAddress(updatedCustomerDto.getAddress());
        }

        customerRepository.save(existingCustomer);
        return "Customer with id " + id + " was successfully updated.";
    }

}
