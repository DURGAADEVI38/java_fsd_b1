package com.proj.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(@NotNull(message = "mandatory")
                      @NotBlank(message = "mandatory")String title,
                      @NotNull(message = "mandatory")
                      @NotBlank(message = "mandatory")
                      String summary) {
}
