package org.example.pensionat.repository;

import org.example.pensionat.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Optional<Customer> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Customer> findByNameAndEmail(String name, String email);

    Optional<Customer> findByEmail(String email);
}
