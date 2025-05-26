package org.example.pensionat.Service;

import org.example.pensionat.dtos.CustomerDto;
import org.example.pensionat.dtos.DetailedCustomerDto;
import org.example.pensionat.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public DetailedCustomerDto entityCustomerToDetailedCustomerDto(Customer customer);

    public CustomerDto entityCustomerToCustomerDto(Customer customer);

    public Customer dtoDetailedCustomerToEntityCustomer(DetailedCustomerDto customerDto);

    public List<DetailedCustomerDto> getAllDetailedCustomer();

    public Optional<DetailedCustomerDto> getDetailedCustomerById(Long id);

    public String deleteCustomer(Long id);

    public String addCustomer(DetailedCustomerDto customerdto);

    public String updateCustomer(Long id, DetailedCustomerDto updatedCustomerDto);

    //sebbe
    //Optional<Customer> findByNameAndEmail(String name, String email);

    public boolean deleteCustomerByNameAndEmail(String name, String email);

    Optional<DetailedCustomerDto> findByNameAndEmail(String name, String email);

    //public String updateCustomer2(DetailedCustomerDto updatedCustomerDto);

    //update - dto customer
    public boolean updateCustomer(String email, DetailedCustomerDto dto);





}