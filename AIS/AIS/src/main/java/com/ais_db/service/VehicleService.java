package com.ais_db.service;

import com.ais_db.dto.PiechartDto;
import com.ais_db.dto.VehicleDto;
import com.ais_db.dto.VehicleRespDo;
import com.ais_db.mapper.VehicleMapper;
import com.ais_db.model.Customer;
import com.ais_db.model.Vehicle;
import com.ais_db.repository.CustomerRepo;
import com.ais_db.repository.VehicleRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class VehicleService {
    private final CustomerRepo customerRepo;
    private final VehicleRepo vehicleRepo;
    private final VehicleMapper vehicleMapper;

    public void vehicleRegister(@Valid VehicleDto vehicleDto, String username) {
        System.out.println("Username from token = " + username);
        Customer customer = customerRepo.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Vehicle vehicle = VehicleMapper.toEntity(vehicleDto);
        vehicle.setCustomer(customer);
        vehicleRepo.save(vehicle);
    }


    public List<VehicleRespDo> getMyVehicles(String username) {
        Customer customer = customerRepo.getByUsername(username) .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Vehicle> vehicles = vehicleRepo.findByCustomer(customer);

        List<VehicleRespDo> vehicleRespDos =
                vehicles.stream()
                        .map(VehicleMapper::maptoVehicle)
                        .toList();
        return vehicleRespDos;
    }

    public List<PiechartDto> getVehicleTypeDistribution() {
        return vehicleRepo.getVehicleTypeDistribution()
                .stream()
                .map(VehicleMapper::toVehicleTypeStat)
                .toList();
    }
}
