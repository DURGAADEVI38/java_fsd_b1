package com.ais_db.repository;

import com.ais_db.dto.VehicleDto;
import com.ais_db.dto.VehicleRespDo;
import com.ais_db.model.Customer;
import com.ais_db.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle,Integer> {

    List<Vehicle> findByCustomer(Customer customer);

    @Query("""
       SELECT v.vehicleType, COUNT(v)
       FROM Vehicle v
       GROUP BY v.vehicleType
       """)
    List<Object[]> getVehicleTypeDistribution();
}
