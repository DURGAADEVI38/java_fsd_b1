package com.proj.demo.dto;

import com.proj.demo.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

public record AppDto(@NotNull(message = "mandatory")
                     Integer jobId,
                     LocalDate appliedAt,
                     @NotNull(message = "mandatory")
                     @NotBlank(message = "mandatory")
                     String jobTitle,
                     Integer seekerId) {
}
