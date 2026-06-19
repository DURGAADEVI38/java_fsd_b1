package com.proj.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobDto(@NotNull(message = "mandatory")
                     @NotBlank(message = "mandatory")
                     String title,
                     @NotNull(message = "mandatory")
                     @NotBlank(message = "mandatory")
                     String description,
                     String location,
                     @NotNull(message = "mandatory")
                     @NotBlank(message = "mandatory")
                     String salary,
                     Integer employerId) {
}
