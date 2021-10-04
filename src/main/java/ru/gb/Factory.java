package ru.gb;

import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class Factory {

    private final EntityManagerFactory managerFactory;

    public Factory() {
        this.managerFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public EntityManagerFactory getManagerFactory() {
        return managerFactory;
    }
}
