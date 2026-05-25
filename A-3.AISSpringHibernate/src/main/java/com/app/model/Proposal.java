package com.app.model;

import com.app.enums.PaymentStatus;
import com.app.enums.ProposalStatus;
import jakarta.persistence.*;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String vehicle;
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne
    private Policy ploicy;
    @ManyToOne
    @JoinColumn(name = "officer_id", nullable = false)
    private InsuranceOfficer insuranceOfficer;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    public Proposal()
    {

    }
    public Proposal(String vehicle,Policy ploicy,PaymentStatus paymentStatus)
    {
        this.vehicle=vehicle;
        this.ploicy=ploicy;
        this.paymentStatus=paymentStatus;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Policy getPloicy() {
        return ploicy;
    }

    public void setPloicy(Policy ploicy) {
        this.ploicy = ploicy;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(ProposalStatus proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", vehicle='" + vehicle + '\'' +
                ", proposalStatus=" + proposalStatus +
                ", paymentStatus=" + paymentStatus +
                ", policy=" + ploicy +
                ", insuranceOfficer=" + insuranceOfficer +
                ", customer=" + customer +
                '}';
    }
}
