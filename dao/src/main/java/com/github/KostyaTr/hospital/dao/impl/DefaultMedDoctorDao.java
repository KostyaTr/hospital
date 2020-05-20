package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.model.MedDoctor;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class DefaultMedDoctorDao implements MedDoctorDao {

    private static class SingletonHolder {
        static final MedDoctorDao HOLDER_INSTANCE = new DefaultMedDoctorDao();
    }

    public static MedDoctorDao getInstance() {
        return DefaultMedDoctorDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<MedDoctor> doctors = session.createQuery("From MedDoctor", MedDoctor.class).list();
        session.getTransaction().commit();
        return doctors;
    }

    @Override
    public MedDoctor getDoctorById(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedDoctor doctor;
        try {
            doctor = session.get(MedDoctor.class, doctorId);
        } catch (NoResultException e){
            doctor = null;
        }
        session.getTransaction().commit();
        return doctor;
    }

    @Override
    public MedDoctor getDoctorByUserId(Long userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedDoctor doctor;
        try {
            doctor = session.createQuery("From MedDoctor where user_id = :user_id", MedDoctor.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            doctor = null;
        }
        session.getTransaction().commit();
        return doctor;
    }
}