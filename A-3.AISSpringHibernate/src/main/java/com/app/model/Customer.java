package com.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Collate;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(nullable = false)
    private String name;
    @Column(length = 1000)
    private String details;
    @OneToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "details='" + details + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
