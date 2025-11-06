package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User
 * Used for API requests and responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 15, message = "Phone number should not exceed 15 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Address should not exceed 100 characters")
    private String address;

    @Size(max = 50, message = "City should not exceed 50 characters")
    private String city;

    @Size(max = 50, message = "State should not exceed 50 characters")
    private String state;

    @Size(max = 50, message = "Country should not exceed 50 characters")
    private String country;

    @Size(max = 10, message = "Zip code should not exceed 10 characters")
    private String zipCode;

    @Size(max = 20, message = "Role should not exceed 20 characters")
    private String role;

    private Boolean active;
}
