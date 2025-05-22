package org.example.pensionat.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Name is required")
    @Size(min=2, max=50)
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phonenumber is required")
    @Pattern(regexp = "^[+]?\\d{7,15}$", message = "Phonenumber must be valid and contain only digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private List<BookingDto> myBookings;
}
