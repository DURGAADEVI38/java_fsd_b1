package com.ais_db.repository;

import com.ais_db.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepo extends JpaRepository<Document,Integer> {

    List<Document> findByProposal_Id(int proposalId);

    List<Document> findByClaimId(int claimId);
}
