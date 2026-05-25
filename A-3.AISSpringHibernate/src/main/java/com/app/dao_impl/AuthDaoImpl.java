package com.app.dao_impl;

import com.app.dao.AuthDao;
import com.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class AuthDaoImpl implements AuthDao {
    @PersistenceContext
    EntityManager em;
    @Override
    public User login(String username, String password) {
        Query sql=em.createQuery("select u from User u where u.name=:username and u.password=:password");
        sql.setParameter("username",username);
        sql.setParameter("password",password);
        return (User) sql.getSingleResult();
    }
}
