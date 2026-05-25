package com.model;

import jakarta.persistence.*;

@Entity

public class InsuranceOfficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    private User user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String designation;
    private String password;
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "InsuranceOfficer{" +
                "designation='" + designation + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", password='" + password + '\'' +
                '}';
    }


}
