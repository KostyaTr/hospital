package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.model.DischargedPatient;
import org.hibernate.Session;

import java.util.List;

public class DefaultDischargedPatientDao implements DischargedPatientDao {
    private int PAGE_SIZE = 10;

    private static class SingletonHolder {
        static final DischargedPatientDao HOLDER_INSTANCE = new DefaultDischargedPatientDao();
    }

    public static DischargedPatientDao getInstance() {
        return DefaultDischargedPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long addDischargedPatient(DischargedPatient dischargedPatient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(dischargedPatient);
        session.getTransaction().commit();
        return dischargedPatient.getDischargedPatientId();
    }

    @Override
    public List<DischargedPatient> getDischargedPatients(int page) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<DischargedPatient> dischargedPatients = session.createQuery("select dp from DischargedPatient dp", DischargedPatient.class)
                .setFirstResult((page - 1) * PAGE_SIZE)
                .setMaxResults(PAGE_SIZE)
                .list();
        session.getTransaction().commit();
        return dischargedPatients;
    }

    @Override
    public double getDischargedPatientsCount() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long count = session.createQuery("select count(dischargedPatientId) from DischargedPatient", Long.class)
                .getSingleResult();
        session.getTransaction().commit();
        return count;
    }
}
