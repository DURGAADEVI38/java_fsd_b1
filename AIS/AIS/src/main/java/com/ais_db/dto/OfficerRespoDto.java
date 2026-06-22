package com.ais_db.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record OfficerRespoDto(@NotNull
                              @NotBlank
                              @Size(min = 4 , message = "Name should be at-least 4 characters")
                              String name,
                              @NotNull
                              @NotBlank
                              @Size(min = 4 , message = "Name should be at-least 4 characters")
                              String username
                              ) {
}
