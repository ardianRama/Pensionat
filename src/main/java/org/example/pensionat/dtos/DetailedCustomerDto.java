package org.example.pensionat.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedCustomerDto {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private List<BookingDto> myBookings;
}
