package com.model;

import com.mysql.cj.exceptions.StreamingNotifiable;
import jakarta.persistence.*;

@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String details;
    @ManyToOne
    private InsuranceOfficer officer;

    private int activePolicy;
    private int ploicyClaimed;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPloicyClaimed() {
        return ploicyClaimed;
    }

    public void setPloicyClaimed(int ploicyClaimed) {
        this.ploicyClaimed = ploicyClaimed;
    }

    public InsuranceOfficer getOfficer() {
        return officer;
    }

    public void setOfficer(InsuranceOfficer officer) {
        this.officer = officer;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "activePolicy=" + activePolicy +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", ploicyClaimed=" + ploicyClaimed +
                '}';
    }
}
