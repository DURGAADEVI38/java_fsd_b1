package com.model;

import com.enums.PaymentStatus;
import com.enums.ProposalStatus;
import jakarta.persistence.*;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 1000)
    private String vehicle;
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne
    private User user;
    @ManyToOne
    private Policy policy;
    @ManyToOne
    private InsuranceOfficer officer;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(ProposalStatus proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public InsuranceOfficer getOfficer() {
        return officer;
    }

    public void setOfficer(InsuranceOfficer officer) {
        this.officer = officer;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", vehicle='" + vehicle + '\'' +
                ", proposalStatus=" + proposalStatus +
                ", paymentStatus=" + paymentStatus +
                ", user_id=" + user +
                ", policy_id=" + policy +
                ", officer_id=" + officer +
                '}';
    }
}
