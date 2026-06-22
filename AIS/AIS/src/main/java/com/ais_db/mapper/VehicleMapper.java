package com.ais_db.mapper;

import com.ais_db.dto.PiechartDto;
import com.ais_db.dto.VehicleDto;
import com.ais_db.dto.VehicleRespDo;
import com.ais_db.enums.VehicleType;
import com.ais_db.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public static Vehicle toEntity(@Valid VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(dto.vehicleType());
        vehicle.setRegistrationNumber(dto.registrationNumber());
        vehicle.setRcNumber(dto.rcNumber());
        vehicle.setBrand(dto.brand());
        vehicle.setModel(dto.model());
        vehicle.setMileage(dto.mileage());
        vehicle.setYearOfPurchase(dto.yearOfPurchase());
        vehicle.setPurchasePrice(dto.purchasePrice());
        return vehicle;
    }


    public static VehicleRespDo maptoVechile(Vehicle vehicle)
    {
        return new VehicleRespDo(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getBrand(),
                vehicle.getModel()
        );
    }

    public static VehicleRespDo maptoVehicle(Vehicle vehicle) {
        return new VehicleRespDo(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getBrand(),
                vehicle.getModel()
        );
    }

    public static PiechartDto toVehicleTypeStat(Object[] row) {
        return new PiechartDto(
                (VehicleType) row[0],
                (Long) row[1]
        );
    }
}
