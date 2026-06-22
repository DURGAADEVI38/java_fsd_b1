package com.ais_db.model;

import com.ais_db.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private String registrationNumber;
    private String rcNumber;
    private String brand;
    private String model;
    private Double mileage;
    private Integer yearOfPurchase;
    private Double purchasePrice;
    private Double currentValue;
    @ManyToOne
    private Customer customer;

}
