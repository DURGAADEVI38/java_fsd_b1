package com.ais_db.repository;

import com.ais_db.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Policyrepo extends JpaRepository<Policy,Integer> {

}
