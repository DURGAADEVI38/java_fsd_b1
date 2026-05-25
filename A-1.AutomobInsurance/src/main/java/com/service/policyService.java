package com.service;

import com.model.Policy;
import com.model.Proposal;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class policyService {
    private final Session session;
    public policyService(Session session)
    {
        this.session=session;
    }
    public Policy getById(int id) {
        Transaction tx= session.beginTransaction();
        Policy policy=session.find(Policy.class,id);
        tx.commit();
        if(policy == null)
        {
            throw new ResourceClosedException(" Invalid Id");
        }
        return policy;
    }
}
