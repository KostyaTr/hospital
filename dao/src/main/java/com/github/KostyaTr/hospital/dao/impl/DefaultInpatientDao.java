package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.model.Inpatient;
import org.hibernate.Session;

import java.util.List;

public class DefaultInpatientDao implements InpatientDao {

    private static class SingletonHolder {
        static final InpatientDao HOLDER_INSTANCE = new DefaultInpatientDao();
    }

    public static InpatientDao getInstance() {
        return DefaultInpatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Inpatient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Inpatient> inpatients = session.createQuery("select p from Inpatient p", Inpatient.class)
                .list();
        session.getTransaction().commit();
        return inpatients;
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Inpatient inpatient = session.get(Inpatient.class, patientId);
        session.getTransaction().commit();
        return inpatient;
    }

    @Override
    public Long addInpatient(Inpatient inpatient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(inpatient);
        session.getTransaction().commit();
        return inpatient.getInpatientId();
    }

    @Override
    public boolean removeInpatientById(Long patientId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete Inpatient where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.getTransaction().commit();
        return getInpatientById(patientId) == null;
    }

    @Override
    public void updateInpatient(Inpatient inpatient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(inpatient);
        session.getTransaction().commit();
    }
}
