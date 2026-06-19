package com.service;

import com.model.InsuranceOfficer;
import com.model.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AuthService {

    private  final Session session;
    public AuthService(Session session) {
        this.session=session;
    }

    public User login(String username, String password) {
        Transaction tx = session.beginTransaction();

        List<User> users = session.createQuery(
                        "from User u where u.name = :name and u.password = :password",
                        User.class)
                .setParameter("name", username.trim())
                .setParameter("password", password.trim())
                .getResultList();

        tx.commit();

        if (users.isEmpty()) {
            throw new NoResultException("User not found");
        }

        return users.get(0);
    }
    public InsuranceOfficer loginOfficer(String username, String password) {

        Transaction tx = session.beginTransaction();

        List<InsuranceOfficer> officers = session.createQuery(
                        "from InsuranceOfficer i where i.name = :name and i.password = :password",
                        InsuranceOfficer.class)
                .setParameter("name", username.trim())
                .setParameter("password", password.trim())
                .getResultList();

        tx.commit();

        if (officers.isEmpty()) {
            throw new NoResultException("Officer not found");
        }

        return officers.get(0);
    }
}
