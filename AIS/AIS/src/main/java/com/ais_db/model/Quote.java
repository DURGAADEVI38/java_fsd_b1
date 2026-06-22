package com.ais_db.model;

import com.ais_db.enums.AddOnType;
import com.ais_db.enums.QuoteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double basePremium;
    private double riskMultiplier;
    private double vehicleTypeFactor;
    private double addonTotal;
    private double finalPremium;
    private LocalDate validTill;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<AddOnType> addons;
    @Enumerated(EnumType.STRING)
    private QuoteStatus quoteStatus;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private Policy policy;
}
