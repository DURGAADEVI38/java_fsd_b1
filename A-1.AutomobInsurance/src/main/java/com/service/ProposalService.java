package com.service;

import com.model.Proposal;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProposalService {
    private final Session session;
    public ProposalService(Session session)
    {
        this.session=session;
    }
    public void insert(Proposal proposal)
    {
        Transaction tx= session.beginTransaction();
        session.persist(proposal);
        tx.commit();
    }

    public void deleteRecord(int id) {
        Transaction tx=session.beginTransaction();
        Proposal proposal=session.find(Proposal.class,id);
        if(proposal==null)
        {
            tx.commit();
            throw  new ResourceClosedException("Invalid Id");
        }
        //session.remove(ticket);
        session.createMutationQuery("delete from Proposal where id=:id")
                .setParameter("id",id)
                .executeUpdate();
        tx.commit();

    }
    public List<Proposal> getAllProposals() {
        Transaction tx=session.beginTransaction();
        List<Proposal> list=session.createQuery("from Proposal ",Proposal.class).list();


        tx.commit();
        return list;
    }
    public Proposal getById(int id) {
        Transaction tx= session.beginTransaction();
        Proposal proposal=session.find(Proposal.class,id);
        tx.commit();
        if(proposal == null)
        {
            throw new ResourceClosedException(" Invalid Id");
        }
        return proposal;
    }
}
