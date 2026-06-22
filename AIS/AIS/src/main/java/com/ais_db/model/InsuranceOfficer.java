package com.ais_db.model;

import com.ais_db.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class InsuranceOfficer {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "insuranceOfficer")
    private List<Policy> policy;
    @OneToOne
    private User user;
}
