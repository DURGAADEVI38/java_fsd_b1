package com.ais_db.dto;

import com.ais_db.enums.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehicleDto(

        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,

        @NotBlank(message = "Registration number is required")
        String registrationNumber,

        @NotBlank(message = "RC number is required")
        String rcNumber,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotBlank(message = "Model is required")
        String model,

        @NotNull(message = "Mileage is required")
        @Positive(message = "Mileage must be greater than 0")
        Double mileage,

        @NotNull(message = "Year of purchase is required")
        @Min(value = 1900, message = "Year must be valid")
        Integer yearOfPurchase,

        @NotNull(message = "Purchase price is required")
        Double purchasePrice
) {}