package com.ais_db.repository;

import com.ais_db.model.InsuranceOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface InsuranceOfficerRepo extends JpaRepository<InsuranceOfficer,Integer> {
    Optional<InsuranceOfficer> findByUserUsername(String username);
}
