package com.ais_db.dto;

import com.ais_db.enums.PolicyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PolicyDto(
                        Integer id,
                        @NotNull(message = "Field sohuld not be empty")
                        @NotBlank(message = "Field sohuld not be empty")
                        String policyName,
                        @NotNull(message = "Field sohuld not be empty")
                        @NotBlank(message = "Field sohuld not be empty")
                        String details,
                        @NotNull(message = "Field sohuld not be empty")
                         int activePolicy,
                        @NotNull(message = "Field sohuld not be empty")
                        int policyClaimed,
                        @NotNull(message = "Validity period is required")
                        LocalDate expiryDate,
                        PolicyStatus status,
                        double basePremium
) {
}
