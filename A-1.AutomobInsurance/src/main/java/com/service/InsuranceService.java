package com.service;

import com.model.InsuranceOfficer;
import com.model.Policy;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InsuranceService {
    private final Session session;
    public InsuranceService(Session session)
    {
        this.session=session;
    }
    public InsuranceOfficer getById(int id) {
        Transaction tx= session.beginTransaction();
        InsuranceOfficer insuranceOfficer =session.find(InsuranceOfficer.class,id);
        tx.commit();
        if(insuranceOfficer== null)
        {
            throw new ResourceClosedException(" Invalid Id");
        }
        return insuranceOfficer;
    }


}
