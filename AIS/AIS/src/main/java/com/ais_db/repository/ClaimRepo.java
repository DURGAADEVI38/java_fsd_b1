package com.ais_db.repository;

import com.ais_db.enums.ClaimStatus;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.model.Claim;
import com.ais_db.model.InsuranceOfficer;
import com.ais_db.model.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.ContentHandler;
import java.util.List;

public interface ClaimRepo extends JpaRepository<Claim,Integer> {
    List<Claim> findByInsuranceOfficerId(int officerId);
    @Query("""
       SELECT c
       FROM Claim c
       JOIN c.customerPolicy cp
       JOIN cp.customer cust
       JOIN cust.user u
       WHERE u.username = :username
       ORDER BY c.createdAt DESC
       """)
    List<Claim> findClaimsByUsername(String username);
    @Query("""
    select c
    from Claim c
    where c.insuranceOfficer.id=:officerId
""")
    Page<Claim> findByInsuranceOfficerId(int officerId, Pageable pageable);

    @Query("""
            select c
            from Claim c
            where c.claimStatus=?1
            """)
    Page<Claim> getbyClaimStatus(ClaimStatus claimStatus, Pageable pageable);
}
