package com.ais_db.model;

import com.ais_db.enums.PaymentStatus;
import com.ais_db.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
    @Column(length = 1000)
    private String remarks;
    private String uploadedDocument;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Vehicle vehicle;
    @OneToOne
    private Quote quote;
    @ManyToOne
    private InsuranceOfficer insuranceOfficer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Policy policy;
}
