package com.app.dao;

import com.app.model.Proposal;

import java.util.List;

public interface ProposalDao {
    void save(Proposal proposal, String username);
    List<Proposal> findAll(String username);

    Proposal getById(int id, String username);

    void update(Proposal proposal);

    void delete(Proposal proposal);
}
