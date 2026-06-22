package com.ais_db.dto;

import com.ais_db.enums.VehicleType;

public record PiechartDto(VehicleType vehicleType,
                          Long count) {
}
