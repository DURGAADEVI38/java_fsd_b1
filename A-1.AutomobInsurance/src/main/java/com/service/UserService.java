package com.service;

import com.model.Policy;
import com.model.User;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {
    private final Session session;
    public UserService(Session session)
    {
        this.session=session;
    }
    public User getById(int id) {
        Transaction tx= session.beginTransaction();
        User user=session.find(User.class,id);
        tx.commit();
        if(user== null)
        {
            throw new ResourceClosedException(" Invalid Id");
        }
        return user;
    }
}
