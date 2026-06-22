package com.ais_db.dto;

import jakarta.validation.constraints.*;

public record UserRespDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Username is required")
        @Size(min = 4, message = "Username must be at least 4 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Contact number is required")
        @Min(value = 1000000000, message = "Invalid contact number")
        @Max(value = 9999999999L, message = "Invalid contact number")
        String contactNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {}