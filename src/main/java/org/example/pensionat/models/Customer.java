package org.example.pensionat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Customer {

    //TODO validering

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    private String address;

    //avvakta
    //private String username;

    //avvakta lite med password
    //private String password;

    @OneToMany(mappedBy = "customer")
    private List<Booking> myBookings;

    public Customer(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
