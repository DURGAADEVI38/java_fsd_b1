package com.ais_db.dto;

import com.ais_db.enums.ClaimStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClaimDto(
        Integer id,
        @NotNull(message = "Customer Policy ID is required")
        Integer customerPolicyId,
        @NotNull(message = "Accident date is required")
        LocalDate accidentDate,
        @NotBlank(message = "Accident location is required")
        String accidentLocation,
        @NotBlank(message = "Accident description is required")
        String accidentDescription,
        @NotNull(message = "Estimated loss amount is required")
        Double estimatedLossAmount) {
}
