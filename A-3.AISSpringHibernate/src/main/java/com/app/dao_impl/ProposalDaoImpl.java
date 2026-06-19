package com.app.dao_impl;

import com.app.dao.CustomerDao;
import com.app.dao.ProposalDao;
import com.app.enums.ProposalStatus;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.InsuranceOfficer;
import com.app.model.Proposal;
import com.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class ProposalDaoImpl implements ProposalDao {
    private CustomerDao customerDao;

    @PersistenceContext
    EntityManager em;
@Autowired
public void setCustomerDao(CustomerDaoImpl customerDao) {
    this.customerDao = customerDao;
}


    @Override
    public void save(Proposal proposal, String username) {
        Customer customer = customerDao.getByUsername(username);
        InsuranceOfficer officer = em.createQuery(
                        "select i from InsuranceOfficer i", InsuranceOfficer.class)
                .setMaxResults(1)
                .getSingleResult();

        proposal.setCustomer(customer);
        proposal.setInsuranceOfficer(officer);
        proposal.setProposalStatus(ProposalStatus.SUBMITTED);
        em.persist(proposal);
    }

    @Override
    public List<Proposal> findAll(String username) {
        TypedQuery<Proposal> query=em.createQuery("select p from Proposal p where p.customer.user.name=:username", Proposal.class);
        query.setParameter("username",username);
        return query.getResultList();
    }
    @Override
    public void delete(Proposal proposal) {
        em.remove(proposal);
    }

    @Override
    public Proposal getById(int id, String username) {
        Proposal proposal = em.find(Proposal.class, id);
        if(proposal == null)
            throw new ResourceNotFoundException("Invalid id given");

        if(proposal.getCustomer().getUser().getId() != id){
            throw new InvalidOwnershipException("You do not own this proposal");
        }
        return proposal;

    }

    @Override
    public void update(Proposal proposal) {
        em.merge(proposal);
    }
}
