package com.app.dao_impl;

import com.app.dao.CustomerDao;
import com.app.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {
    @PersistenceContext
    EntityManager em;

    @Override
    public Customer getByUsername(String username) {

            String sql="select c from Customer c where c.user.name=?1";
            TypedQuery<Customer> query=em.createQuery(sql, Customer.class);
            query.setParameter(1,username);
            return query.getSingleResult();
    }
}
