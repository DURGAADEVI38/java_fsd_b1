package com.ais_db.model;

import com.ais_db.enums.PolicyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CustomerPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Policy policy;
    @ManyToOne
    private Vehicle vehicle;
    @OneToOne(fetch = FetchType.LAZY)
    private Proposal proposal;
    private LocalDate purchaseDate;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private String couponCode;
    @Enumerated(EnumType.STRING)
    private PolicyStatus status;
}
