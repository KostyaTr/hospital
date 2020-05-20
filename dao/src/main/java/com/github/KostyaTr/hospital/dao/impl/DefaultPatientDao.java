package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.model.Patient;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class DefaultPatientDao implements PatientDao {

    private static class SingletonHolder{
        static final PatientDao HOLDER_INSTANCE = new DefaultPatientDao();
    }

    public static PatientDao getInstance(){
        return DefaultPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Patient> patients = session.createQuery("select p from Patient p where doctor_id = :doctor_id",
                Patient.class)
                .setParameter("doctor_id", doctorId)
                .list();
        session.getTransaction().commit();
        return patients;
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int coupon;
        try {
            coupon = session.createQuery("select max(couponNum) from Patient " +
                    "where doctor_id = :doctor_id and  day(visit_date) = :day", Integer.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            coupon = 0;
        }
        return coupon;
    }

    @Override
    public List<Patient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Patient> patients = session.createQuery("select p from Patient p", Patient.class)
                .list();
        session.getTransaction().commit();
        return patients;
    }

    @Override
    public Long addPatient(Patient patient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(patient);
        session.getTransaction().commit();
        return patient.getPatientId();
    }

    @Override
    public boolean removePatientById(Long patientId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete Patient where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.getTransaction().commit();
        return getPatientById(patientId) == null;
    }

    @Override
    public Patient getPatientById(Long patientId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Patient patient = session.get(Patient.class, patientId);
        session.getTransaction().commit();
        return patient;
    }

    @Override
    public void updateVisitDate(Patient patient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(patient);
        session.getTransaction().commit();
    }

    @Override
    public Date getLatestTimeToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        java.util.Date date;
        try {
            date = session.createQuery("select max(visitDate) from Patient " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", Date.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        return date;
    }
}