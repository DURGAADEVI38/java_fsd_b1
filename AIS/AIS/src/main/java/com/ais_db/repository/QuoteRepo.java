package com.ais_db.repository;

import com.ais_db.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepo extends JpaRepository<Quote,Integer> {
}
