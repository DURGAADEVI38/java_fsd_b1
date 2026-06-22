package com.ais_db.model;

import com.ais_db.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate accidentDate;
    private String accidentLocation;
    private String accidentDescription;
    private double estimatedLossAmount;
    private double approvedAmount;
    private String uploadedDocument;
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;
    @CreationTimestamp
    private Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerPolicy customerPolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    private InsuranceOfficer insuranceOfficer;
}
