package com.app.dao_impl;

import com.app.dao.PolicyDao;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Policy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PolicyDaoImpl implements PolicyDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Policy getById(int id) {

        Policy policy = em.find(Policy.class, id);

        if (policy == null) {
            throw new ResourceNotFoundException("Policy not found with id: " + id);
        }

        return policy;
    }
}
