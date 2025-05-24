package org.example.pensionat.service.impl;

import jakarta.persistence.Column;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;
import org.example.pensionat.repository.CustomerRepository;
import org.example.pensionat.service.BookingService;
import org.example.pensionat.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, bookingService);

    private long id = 1L;

    private String name = "Bosse";

    private String email = "bosse@gmail.com";

    private String phoneNumber = "123456789";

    private String address = "Långtbortistan 123";

    Customer customer = new Customer(id, name, email, phoneNumber, address, new ArrayList<>());

    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(id).name(name).email(email).phoneNumber(phoneNumber)
            .address(address).build();

    @Test
    void entityCustomerToDetailedCustomerDto() {
        DetailedCustomerDto actual = customerServiceImpl.entityCustomerToDetailedCustomerDto(customer);

        assertEquals(actual.getId(), detailedCustomerDto.getId(), "Should be the same id");
        assertEquals(actual.getName(), detailedCustomerDto.getName(), "Should be the same name");
        assertEquals(actual.getEmail(), detailedCustomerDto.getEmail(), "Should be the same email");
        assertEquals(actual.getAddress(), detailedCustomerDto.getAddress(), "Should be the same address");
    }

    @Test
    void detailedCustomerToEntityCustomerDto() {
        Customer actual = customerServiceImpl.dtoDetailedCustomerToEntityCustomer(detailedCustomerDto);

        assertEquals(actual.getId(), detailedCustomerDto.getId(), "Should be the same id");
        assertEquals(actual.getName(), detailedCustomerDto.getName(), "Should be the same name");
        assertEquals(actual.getEmail(), detailedCustomerDto.getEmail(), "Should be the same email");
        assertEquals(actual.getAddress(), detailedCustomerDto.getAddress(), "Should be the same address");
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepository, bookingService);
        List<DetailedCustomerDto> allCustomers = service2.getAllDetailedCustomer();

        assertTrue(allCustomers.size() == 1);
    }
}