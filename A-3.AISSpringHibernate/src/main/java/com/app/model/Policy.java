package com.app.model;

import jakarta.persistence.*;

import java.util.function.IntUnaryOperator;

@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String details;
    private int activePolicy;
    private int policyClaimed;
    @ManyToOne
    private InsuranceOfficer insuranceOfficer;

    public int getActivePolicy() {
        return activePolicy;
    }

    public void setActivePolicy(int activePolicy) {
        this.activePolicy = activePolicy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InsuranceOfficer getInsuranceOfficer() {
        return insuranceOfficer;
    }

    public void setInsuranceOfficer(InsuranceOfficer insuranceOfficer) {
        this.insuranceOfficer = insuranceOfficer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPolicyClaimed() {
        return policyClaimed;
    }

    public void setPolicyClaimed(int policyClaimed) {
        this.policyClaimed = policyClaimed;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "activePolicy=" + activePolicy +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", policyClaimed=" + policyClaimed +
                ", insuranceOfficer=" + insuranceOfficer +
                '}';
    }
}
