package com.config;


import com.model.InsuranceOfficer;
import com.model.Policy;
import com.model.Proposal;
import com.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory()
    {
        if(sessionFactory==null)
        {
            Configuration configuration=new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/automobIns?createDatabaseIfNotExist=true");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "Durgaa@04");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.addAnnotatedClass(Proposal.class);
            configuration.addAnnotatedClass(Policy.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(InsuranceOfficer.class);
            return configuration.buildSessionFactory();
        }
        return sessionFactory;

    }
    public  void closeFactory()
    {
        sessionFactory.close();
    }

}
