package com.ais_db.model;

import com.ais_db.enums.PolicyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String policyName;
    private LocalDate startDate;
    @Column(length = 2000)
    private String details;
    private double basePremium;
    private int activePolicy;
    private int policyClaimed;
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private PolicyStatus policyStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private InsuranceOfficer insuranceOfficer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;   // who owns this policy

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;     // insured vehicle




}
