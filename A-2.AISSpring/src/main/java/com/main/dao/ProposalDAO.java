package com.main.dao;

import com.main.model.Proposal;

import java.util.List;

public interface ProposalDAO {
    void update(Proposal proposal);
    void delete(int id);
    void insert(Proposal proposal);
    Proposal getByID(int id);
    List<Proposal> getAll();


}
