package org.example.pensionat.service.impl;

import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;
import org.example.pensionat.repository.CustomerRepository;
import org.example.pensionat.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private CustomerServiceImpl customerServiceImpl;

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

        assertEquals(customer.getId(), actual.getId(), "Should be the same id");
        assertEquals(customer.getName(), actual.getName(), "Should be the same name");
        assertEquals(customer.getEmail(), actual.getEmail(), "Should be the same email");
        assertEquals(customer.getAddress(), actual.getAddress(), "Should be the same address");
    }

    @Test
    void dtoDetailedCustomerToEntityCustomer() {
        Customer actual = customerServiceImpl.dtoDetailedCustomerToEntityCustomer(detailedCustomerDto);

        assertEquals(detailedCustomerDto.getId(), actual.getId(), "Should be the same id");
        assertEquals(detailedCustomerDto.getName(), actual.getName(), "Should be the same name");
        assertEquals(detailedCustomerDto.getEmail(), actual.getEmail(), "Should be the same email");
        assertEquals(detailedCustomerDto.getAddress(), actual.getAddress(), "Should be the same address");
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepository, bookingService);
        List<DetailedCustomerDto> allCustomers = service2.getAllDetailedCustomer();

        assertTrue(allCustomers.size() == 1);
    }
}