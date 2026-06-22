package com.ais_db.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordUpdateDto(@NotBlank
                                 String username,
                                @NotBlank
                                 @Size(min = 4, message = "Password must be at least 4 characters")
                                 String newPassword) {
}
