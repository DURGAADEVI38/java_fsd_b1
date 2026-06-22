package com.ais_db.repository;

import com.ais_db.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    @Query("""
           SELECT c
           FROM Customer c
           WHERE c.user.username = :username
           """)
    Optional<Customer> getByUsername(String username);


}
