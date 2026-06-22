package com.ais_db.repository;

import com.ais_db.model.CustomerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerPolicyRepo extends JpaRepository<CustomerPolicy,Integer> {
    @Query("""
SELECT cp FROM CustomerPolicy cp
            JOIN cp.customer c 
            JOIN c.user u 
            WHERE u.username = :username""")
    List<CustomerPolicy> findPoliciesByUsername(String username);
}
