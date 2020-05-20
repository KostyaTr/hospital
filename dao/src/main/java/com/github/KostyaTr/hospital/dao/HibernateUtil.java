package com.github.KostyaTr.hospital.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManagerFactory emFactory = null;

    public static Session getSession() {
        return getEm().unwrap(Session.class);
    }

    private static EntityManager getEm() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("hospital");
        }
        return emFactory.createEntityManager();
    }


    public static void closeEMFactory() {
        emFactory.close();
    }
}
