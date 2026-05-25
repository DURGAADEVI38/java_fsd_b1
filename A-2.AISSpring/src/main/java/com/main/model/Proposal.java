package com.main.model;


import com.main.enums.PaymentStatus;
import com.main.enums.ProposalStatus;

public class Proposal {
    private int id;
    private String vehicle;
    private ProposalStatus proposalStatus;
    private PaymentStatus paymentStatus;
    private int user_id;
    private int policy_id;
    private  int officer_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(int officer_id) {
        this.officer_id = officer_id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(ProposalStatus proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Proposal(int id, int officer_id, PaymentStatus paymentStatus, int policy_id, ProposalStatus proposalStatus, int user_id, String vehicle) {
        this.id = id;
        this.officer_id = officer_id;
        this.paymentStatus = paymentStatus;
        this.policy_id = policy_id;
        this.proposalStatus = proposalStatus;
        this.user_id = user_id;
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", vehicle='" + vehicle + '\'' +
                ", proposalStatus=" + proposalStatus +
                ", paymentStatus=" + paymentStatus +
                ", user_id=" + user_id +
                ", policy_id=" + policy_id +
                ", officer_id=" + officer_id +
                '}';
    }
}
