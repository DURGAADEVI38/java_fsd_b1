package com.ais_db.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDto( String username,

                                @NotBlank
                                 String newPassword) {
}
