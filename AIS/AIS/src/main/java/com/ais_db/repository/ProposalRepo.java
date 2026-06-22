package com.ais_db.repository;

import com.ais_db.dto.HeadProposalDto;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.model.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProposalRepo extends JpaRepository<Proposal,Integer> {


    @Query("""
            select p
            from Proposal  p
            where p.proposalStatus=?1
            """)
    Page<Proposal> getbyProposalStatus(ProposalStatus proposalStatus, Pageable pageable);

    @Query("""
    select p
    from Proposal p
    where p.insuranceOfficer.id = :officerId
""")
    Page<Proposal> findByOfficerId(int officerId, Pageable pageable);

    @Query("""
            select p
            from Proposal  p
            where p.customer.user.username=?1
            """)
    Page<Proposal> getByUserName(String username, Pageable pageable);
    @Query("""
            SELECT p.proposalStatus, COUNT(p) FROM Proposal p GROUP BY p.proposalStatus
    """)
    List<Object[]> getProposalStatusCount();

    long countByCustomer_User_Username(String username);
}
